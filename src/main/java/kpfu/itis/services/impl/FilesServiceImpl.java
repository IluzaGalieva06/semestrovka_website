package kpfu.itis.services.impl;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.exceptions.NotFoundException;
import kpfu.itis.models.FileInfo;
import kpfu.itis.repositories.ConcertRepository;
import kpfu.itis.repositories.FilesRepository;
import kpfu.itis.repositories.UserRepository;
import kpfu.itis.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {

    private final String path;
    private final FilesRepository filesRepository;
    private final UserRepository usersRepository;
    private final ConcertRepository concertRepository;

    public FilesServiceImpl(String path, FilesRepository filesRepository, UserRepository usersRepository, ConcertRepository concertRepository) {
        this.path = path;
        this.filesRepository = filesRepository;
        this.usersRepository = usersRepository;
        this.concertRepository = concertRepository;

    }

    @Override
    public FileInfo saveFileToStorage(UserDto user, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
            user.setAvatarId(fileInfo.getId());
            usersRepository.updateAvatarForUser(user.getEmail(), fileInfo.getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }
    @Override
    public FileInfo saveFileToStoragePoster(ConcertForm form, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
            form.setPosterId(fileInfo.getId());
            concertRepository.updatePosterForConcert(form.getName(), fileInfo.getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }


    @Override
    public void readFileFromStorage(Long fileId, OutputStream outputStream) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
        FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new NotFoundException("File not found"));


        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(Long fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new NotFoundException("File not found"));
    }
    @Override
    public FileInfo updateAvatar(UserDto user, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
            usersRepository.updateAvatarForUser(user.getEmail(), fileInfo.getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }
}
