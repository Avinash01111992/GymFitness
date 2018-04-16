package com.example.suma.physicalfitness.pojo;

import java.io.Serializable;

/**
 * Created by avinash on 3/4/18.
 */

public class CustomerTaskListPojo implements Serializable {

public String bodyType;
public String excType;
public String eqpType;
public String uname;

public CustomerTaskListPojo()
{


}

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getExcType() {
        return excType;
    }

    public void setExcType(String excType) {
        this.excType = excType;
    }

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public CustomerTaskListPojo(String uname,String bodType, String exType, String epType)
{
    this.bodyType = bodType;
    this.excType = exType;
    this.eqpType = epType;
    this.uname = uname;

}

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
