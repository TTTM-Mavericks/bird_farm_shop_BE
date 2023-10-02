package com.tttm.birdfarmshop.Service;

import com.tttm.birdfarmshop.DTO.MailDTO;
import jakarta.servlet.http.HttpSession;

public interface CodeStorageService {
     void storeCodeInSession(MailDTO dto, HttpSession session);
     String getCodeFromSession(MailDTO dto, HttpSession session);

}
