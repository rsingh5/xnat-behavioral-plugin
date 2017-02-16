package org.bidmc.xnat.behavioral.subjectmapping.repositories;

import org.bidmc.xnat.behavioral.subjectmapping.SubjectMapping;
import org.nrg.framework.orm.hibernate.AbstractHibernateDAO;

import org.springframework.stereotype.Repository;

@Repository
public class SubjectMappingRepository extends AbstractHibernateDAO<SubjectMapping> {
}
