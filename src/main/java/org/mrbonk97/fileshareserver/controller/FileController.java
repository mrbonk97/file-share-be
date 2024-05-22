//package org.mrbonk97.fileshareserver.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.mrbonk97.fileshareserver.dao.Response;
//import org.mrbonk97.fileshareserver.dao.fileData.request.UploadFileRequest;
//import org.mrbonk97.fileshareserver.dao.fileData.response.UploadFileResponse;
//import org.mrbonk97.fileshareserver.model.User;
//import org.mrbonk97.fileshareserver.model.FileData;
//import org.mrbonk97.fileshareserver.service.AccountService;
//import org.mrbonk97.fileshareserver.service.DatabaseStorageService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RequestMapping("/api/files")
//@RequiredArgsConstructor
//@RestController
//public class FileController {
//    private final DatabaseStorageService storageService;
//    private final AccountService accountService;
//
//    @PostMapping
//    public Response<UploadFileResponse> uploadFile(@ModelAttribute UploadFileRequest uploadFileRequest, Authentication authentication) throws IOException {
//        String email = authentication.getName();
//        User user = accountService.loadByEmail(email);
//        FileData fileData = storageService.uploadFile(uploadFileRequest.getFile(), user);
//        return Response.success(UploadFileResponse.of(fileData));
//    }
//
//    @GetMapping("/preview/{filename}")
//    public ResponseEntity<byte[]> preview(@PathVariable String filename, Authentication authentication) {
//        String email = authentication.getName();
//        User user = accountService.loadByEmail(email);
//        FileData fileData = storageService.downloadFile(filename, user);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(fileData.getContentType())).body(fileData.getDecompressedData());
//    }
//
//    @GetMapping("/{filename}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename, Authentication authentication) {
//        String email = authentication.getName();
//        User user = accountService.loadByEmail(email);
//        FileData fileData = storageService.downloadFile(filename, user);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileData.getOriginalFileName()))
//                .contentType(MediaType.valueOf(fileData.getContentType()))
//                .body(fileData.getDecompressedData());
//    }
//
////    @GetMapping
////    public Response<Page<FileData>> getFiles(Pageable pageable, Authentication authentication) {
////        String email = authentication.getName();
////        Account account = accountService.loadByEmail(email);
////        Page<FileData> page = storageService.getFiles(pageable,account);
////        return Response.success(page);
////    }
//
//}
