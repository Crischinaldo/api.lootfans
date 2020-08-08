package org.web.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.web.restapi.model.FileMetaData;

@Repository
public interface FileMetaDataRepository
        extends JpaRepository<FileMetaData, Integer>, JpaSpecificationExecutor<FileMetaData> {


}