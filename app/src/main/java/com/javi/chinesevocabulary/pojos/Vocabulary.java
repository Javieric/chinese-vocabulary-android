package com.javi.chinesevocabulary.pojos;

import java.util.List;

/**
 * Created by javi on 22/10/2016.
 */

public class Vocabulary {

    private List<Resource> resources;
    private int version;

    public Vocabulary() {
    }

    public Vocabulary(List<Resource> resources, int version) {
        this.resources = resources;
        this.version = version;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
