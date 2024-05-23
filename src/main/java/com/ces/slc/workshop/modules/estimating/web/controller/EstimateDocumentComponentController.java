package com.ces.slc.workshop.modules.estimating.web.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ces.slc.workshop.modules.core.application.document.DocumentComponentSpecificationBuilder;
import com.ces.slc.workshop.modules.core.web.controller.AbstractDocumentComponentController;
import com.ces.slc.workshop.modules.core.web.dto.DocumentComponentIdentifierDto;
import com.ces.slc.workshop.modules.estimating.application.EstimateDocumentMapper;
import com.ces.slc.workshop.modules.estimating.application.EstimateDocumentService;
import com.ces.slc.workshop.modules.estimating.domain.EstimateComponent;
import com.ces.slc.workshop.modules.estimating.domain.EstimateDocument;
import com.ces.slc.workshop.modules.estimating.web.dto.EstimateComponentDto;
import com.ces.slc.workshop.support.ResponseEntitySupport;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/estimate")
public class EstimateDocumentComponentController extends AbstractDocumentComponentController<
        EstimateDocument, EstimateComponent,
        EstimateComponentDto> {

    protected EstimateDocumentComponentController(
            EstimateDocumentService estimateDocumentService,
            EstimateDocumentMapper documentMapper,
            DocumentComponentSpecificationBuilder<EstimateComponent> specificationBuilder) {
        super(specificationBuilder, estimateDocumentService, documentMapper);
    }

    @GetMapping("/{id}/components/{componentId}/children")
    public ResponseEntity<Set<DocumentComponentIdentifierDto>> getComponentChildren(
            @PathVariable Long id,
            @PathVariable Long componentId) {
        return ResponseEntitySupport.fromOptionalCollection(
                getDocumentService().getComponentChildren(id, componentId),
                getDocumentMapper()::toComponentIdentifierDto
        );
    }

    @PostMapping("/{id}/components/{componentId}/children")
    public ResponseEntity<DocumentComponentIdentifierDto> createComponentChild(
            @PathVariable Long id,
            @PathVariable Long componentId,
            @Valid @RequestBody EstimateComponentDto componentDto) {
        return ResponseEntitySupport.fromOptional(
                getDocumentService().createComponentChild(id, componentId, componentDto),
                getDocumentMapper()::toComponentIdentifierDto
        );
    }

    @Override
    public EstimateDocumentService getDocumentService() {
        return (EstimateDocumentService) super.getDocumentService();
    }
}