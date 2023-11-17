package kpfu.itis.services;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.models.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;

public interface FilesService {
    FileInfo saveFileToStorage(UserDto user, InputStream file, String originalFileName, String contentType, Long size);
    FileInfo saveFileToStoragePoster(ConcertForm form, InputStream inputStream, String originalFileName, String contentType, Long size);
    void readFileFromStorage(Long fileId, OutputStream outputStream);
    FileInfo getFileInfo(Long fileId);
    FileInfo updateAvatar(UserDto user, InputStream file, String originalFileName, String contentType, Long size);
}
