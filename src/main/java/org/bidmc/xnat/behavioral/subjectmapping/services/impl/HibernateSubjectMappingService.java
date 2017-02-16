package org.bidmc.xnat.behavioral.subjectmapping.services.impl;

import org.bidmc.xnat.behavioral.subjectmapping.SubjectMapping;
import org.bidmc.xnat.behavioral.subjectmapping.repositories.SubjectMappingRepository;
import org.bidmc.xnat.behavioral.subjectmapping.services.SubjectMappingService;
import org.nrg.framework.orm.hibernate.AbstractHibernateEntityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HibernateSubjectMappingService extends AbstractHibernateEntityService<SubjectMapping, SubjectMappingRepository> implements SubjectMappingService {
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public SubjectMapping findBySubjectId(final String subjectId) {
        return getDao().findByUniqueProperty("subjectId", subjectId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public SubjectMapping findByRecordId(final String recordId, final String source) {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("recordId", recordId);
        properties.put("source", source);
        final List<SubjectMapping> subjectMappings = getDao().findByProperties(properties);
        if (subjectMappings == null || subjectMappings.size() == 0) {
            return null;
        }
        return subjectMappings.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<SubjectMapping> findBySource(final String source) {
        return getDao().findByProperty("source", source);
    }
}
