/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class UploadFilesResponseDTO {

    List<String> downloadUrls;

    public UploadFilesResponseDTO() {
        downloadUrls = new ArrayList<>();
    }

    public List<String> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

}
