package cn.algerfan.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author zsh
 * @company wlgzs
 * @create 2018-12-15 16:15
 * @Describe
 */
public interface StorageService {

    Path load(String filename);

    Resource loadAsResource(String filename);

}