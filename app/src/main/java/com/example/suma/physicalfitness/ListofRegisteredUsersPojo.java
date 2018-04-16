package com.example.suma.physicalfitness;

import com.example.suma.physicalfitness.pojo.RegistrationPojo;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by avinash on 2/4/18.
 */

@IgnoreExtraProperties


public class ListofRegisteredUsersPojo implements Serializable {


    public List<RegistrationPojo> eachUserPojoList;


    public List<RegistrationPojo> getEachUserPojoList() {
        return eachUserPojoList;
    }

    public void setEachUserPojoList(List<RegistrationPojo> eachUserPojoList) {
        this.eachUserPojoList = eachUserPojoList;
    }
}
