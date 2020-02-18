package com.kajalbordhon.data;

import java.util.Arrays;
import java.util.Objects;

/**
 * Attachment bean for both regular and embedded attachment
 *
 * @author kajal
 */
public class AttachmentBean {

    private int id = -1;
    private byte[] attachment;
    private String name;
    private boolean embedded;

    public AttachmentBean() {
    }

    public AttachmentBean(byte[] attachment, String name, boolean embedded) {
        this.attachment = attachment;
        this.name = name;
        this.embedded = embedded;
    }

    public int getId() {
        return id;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public String getName() {
        return name;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.hashCode(this.attachment);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + (this.embedded ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttachmentBean other = (AttachmentBean) obj;
        if (this.embedded != other.embedded) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Arrays.equals(this.attachment, other.attachment);
    }

}
