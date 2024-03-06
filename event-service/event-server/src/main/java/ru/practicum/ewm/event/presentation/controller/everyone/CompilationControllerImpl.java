package ru.practicum.ewm.event.presentation.controller.everyone;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.compilation.CompilationService;
import ru.practicum.ewm.event.presentation.dto.CompilationResponse;
import ru.practicum.ewm.event.presentation.mapper.CompilationMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompilationControllerImpl implements CompilationController {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationResponse> findCompilations(Boolean pinned, long from, int size) {
        return compilationMapper.toCompilationResponse(compilationService.findCompilations(pinned, from, size));
    }

    @Override
    public CompilationResponse findCompilation(long compilationId) {
        return compilationMapper.toCompilationResponse(compilationService.findCompilation(compilationId));
    }
}
