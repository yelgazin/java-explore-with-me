package ru.practicum.ewm.event.presentation.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.compilation.CompilationService;
import ru.practicum.ewm.event.presentation.dto.CompilationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CompilationResponse;
import ru.practicum.ewm.event.presentation.dto.CompilationUpdateRequest;
import ru.practicum.ewm.event.presentation.mapper.CompilationMapper;

@RestController
@RequiredArgsConstructor
public class AdminCompilationControllerImpl implements AdminCompilationController {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationResponse createCompilation(CompilationCreateRequest compilationCreateRequest) {
        return compilationMapper.toCompilationResponse(
                compilationService.createCompilations(compilationMapper.toCompilationEntity(compilationCreateRequest))
        );
    }

    @Override
    public CompilationResponse updateCompilation(long compilationId, CompilationUpdateRequest compilationUpdateRequest) {
        return compilationMapper.toCompilationResponse(
                compilationService.updateCompilation(compilationId,
                        compilationMapper.toCompilationUpdateParameters(compilationUpdateRequest))
        );
    }

    @Override
    public void deleteCompilation(long compilationId) {
        compilationService.deleteCompilation(compilationId);
    }
}
