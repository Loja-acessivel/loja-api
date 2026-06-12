package br.com.apiloja.Mapper;

import java.util.ArrayList;
import java.util.List;

public interface BaseMapper<REQ, RESP, ENT> {
    ENT toEntity(REQ dto);
    RESP toResponse(ENT ent);

    default List<RESP> toResponseList(List<ENT> entList){
        List <RESP> list = new ArrayList<>();
        for (ENT ent : entList){
            RESP resp = toResponse(ent);
            list.add(resp);
        }
        return list;
    }
}
