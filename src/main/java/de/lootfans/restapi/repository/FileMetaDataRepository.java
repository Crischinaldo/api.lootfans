package de.lootfans.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import de.lootfans.restapi.model.FileMetaData;

@Repository
public interface FileMetaDataRepository
        extends JpaRepository<FileMetaData, Integer>, JpaSpecificationExecutor<FileMetaData> {


}