package com.example.itaem.service;


import com.example.itaem.VO.PreparerEditVo;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.VO.RegisterVo;
import com.example.itaem.VO.SuggestionVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PreparerService {

    PreparerVo getMsg(HttpServletResponse response, String phonecall);

    void changeMsg(HttpServletRequest request, HttpServletResponse response, PreparerEditVo preparerEditVo, PreparerVo preparerVo);

    void checkMail(String mail, PreparerVo preparerVo);

    void setMail(PreparerVo loginU, String mail, String code);

    /**
     * 建议模块
     * @param suggestionVo
     * @param loginU
     */
    void suggest(SuggestionVo suggestionVo, PreparerVo loginU);

    void quit(HttpServletRequest request, HttpServletResponse response);


}
