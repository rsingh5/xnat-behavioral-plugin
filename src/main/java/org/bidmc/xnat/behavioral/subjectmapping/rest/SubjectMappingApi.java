/*
 * xnat-workshop-plugin: org.nrg.xnat.workshop.subjectmapping.rest.SubjectMappingApi
 * XNAT http://www.xnat.org
 * Copyright (c) 2016, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.bidmc.xnat.behavioral.subjectmapping.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.bidmc.xnat.behavioral.subjectmapping.SubjectMapping;
import org.bidmc.xnat.behavioral.subjectmapping.preferences.SubjectMappingPreferencesBean;
import org.bidmc.xnat.behavioral.subjectmapping.services.SubjectMappingService;
import org.nrg.framework.annotations.XapiRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api
@XapiRestController
@RequestMapping(value = "/subjectmapping")
public class SubjectMappingApi {
    @Autowired
    public void setSubjectMappingService(final SubjectMappingService subjectMappingService) {
        _subjectMappingService = subjectMappingService;
    }

    @Autowired
    final void setSubjectMappingPreferencesBean(final SubjectMappingPreferencesBean preferences) {
        _preferences = preferences;
    }

    @ApiOperation(value = "Returns a list of all subject mappings.",
                  notes = "Disregards source system.",
                  response = SubjectMapping.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Subject mappings successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<List<SubjectMapping>> getEntities() {
        return new ResponseEntity<>(_subjectMappingService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Creates a new subject mapping.",
                  notes = "Disregards source system.",
                  response = SubjectMapping.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Subject mapping successfully created."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<SubjectMapping> createEntity(@RequestBody final SubjectMapping entity) {
        final SubjectMapping created = _subjectMappingService.create(entity);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves the indicated subject mapping.",
                  notes = "Based on primary key ID, not subject or record ID.",
                  response = SubjectMapping.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Subject mapping successfully retrieved."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SubjectMapping> getEntity(@PathVariable final Long id) {
        return new ResponseEntity<>(_subjectMappingService.retrieve(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates the indicated subject mapping.",
                  notes = "Based on primary key ID, not subject or record ID.",
                  response = Void.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Subject mapping successfully updated."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateEntity(@PathVariable final Long id, @RequestBody final SubjectMapping entity) {
        final SubjectMapping existing = _subjectMappingService.retrieve(id);
        existing.setSubjectId(entity.getSubjectId());
        existing.setSource(entity.getSource());
        _subjectMappingService.update(existing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes the indicated subject mapping.",
                  notes = "Based on primary key ID, not subject or record ID.",
                  response = Void.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Subject mapping successfully deleted."),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEntity(@PathVariable final Long id) {
        final SubjectMapping existing = _subjectMappingService.retrieve(id);
        _subjectMappingService.delete(existing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a list of the source system IDs.", response = String.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Source system IDs successfully retrieved."),
            @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
            @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(value = "sources", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getSourceIds() {
        return new ResponseEntity<>(_preferences.getSourceSystemIds(), HttpStatus.OK);
    }

    @ApiOperation(value = "Sets the submitted source system IDs.",
            response = Void.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Source system IDs successfully created."),
            @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
            @ApiResponse(code = 500, message = "Unexpected error")})
    @RequestMapping(value = "sources", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<Void> setSourceIds(@RequestBody final List<String> sourceIds) {
        _preferences.setSourceSystemIds(sourceIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private SubjectMappingService         _subjectMappingService;
    private SubjectMappingPreferencesBean _preferences;
}
