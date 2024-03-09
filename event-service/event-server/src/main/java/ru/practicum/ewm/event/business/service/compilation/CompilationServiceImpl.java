package ru.practicum.ewm.event.business.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.CompilationCopier;
import ru.practicum.ewm.event.business.dto.CompilationUpdateParameters;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.repository.CompilationRepository;
import ru.practicum.ewm.event.persistence.repository.EventRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationCopier compilationCopier;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationEntity> findCompilations(Boolean pinned, long from, int size) {
        log.info("Поиск подборок. Начиная с {}, количество объектов {}, закреплена {}.", from, size, pinned);

        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, PageableUtil.of(from, size));
        } else {
            return compilationRepository.findAll(PageableUtil.of(from, size)).toList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationEntity findCompilation(long compilationId) {
        log.info("Поиск подборки по id {}.", compilationId);

        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException(
                                format("Сборка с id {} не найдена.", compilationId),
                                "Отсутствуют сведения в базе данных."
                        )
                );
    }

    @Override
    public CompilationEntity createCompilations(CompilationEntity compilationEntity) {
        log.info("Создание подборки. {}.", compilationEntity);

        List<EventEntity> eventList = eventRepository.findAllById(compilationEntity.getEvents().stream()
                .map(EventEntity::getId)
                .collect(Collectors.toList()));

        compilationEntity.setEvents(new HashSet<>(eventList));

        return compilationRepository.save(compilationEntity);
    }

    @Override
    public CompilationEntity updateCompilation(long compilationId, CompilationUpdateParameters updateParameters) {
        log.info("Создание подборки c id {}. Параметры обновления {}.", compilationId, updateParameters);

        CompilationEntity savedCompilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException(
                                format("Сборка с id {} не найдена.", compilationId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        compilationCopier.update(savedCompilation, updateParameters);
        updateEventsList(savedCompilation, updateParameters.getEvents());

        return savedCompilation;
    }

    private void updateEventsList(CompilationEntity savedCompilation, Set<Long> events) {
        if (events != null && !events.isEmpty()) {
            List<EventEntity> eventList = eventRepository.findAllById(events);
            savedCompilation.setEvents(new HashSet<>(eventList));
        }
    }

    @Override
    public void deleteCompilation(long compilationId) {
        log.info("Удаление подборки c id {}.", compilationId);

        if (!compilationRepository.existsById(compilationId)) {
            throw new NotFoundException(
                    format("Сборка с id {} не найдена.", compilationId),
                    "Отсутствуют сведения в базе данных."
            );
        }

        compilationRepository.deleteById(compilationId);
    }
}
