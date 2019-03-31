package com.irvin.markov.service;

import com.irvin.markov.entity.Message;
import org.springframework.web.multipart.MultipartFile;

public interface TextUtilityService {

    public Message getMarkovString(String data, int prefixLength, int suffixLength, int outputSize);

}
