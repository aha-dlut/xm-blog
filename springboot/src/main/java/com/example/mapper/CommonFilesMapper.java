package com.example.mapper;

import com.example.entity.CommonFiles;
import com.example.entity.DiskFiles;

import java.nio.file.Files;
import java.util.List;

/*
@author TanCheng
@create 2024 -03 -28    
*/
public interface CommonFilesMapper {

    void insert(CommonFiles commonFiles);

    CommonFiles selectById(Integer id);

    void updateById(CommonFiles commonFiles);

    void deleteById(Integer id);

    List<CommonFiles> selectByFolderId(Integer id);

    List<CommonFiles> selectAll(CommonFiles commonFiles);

    List<CommonFiles> selectAllData(CommonFiles commonFiles);
}
