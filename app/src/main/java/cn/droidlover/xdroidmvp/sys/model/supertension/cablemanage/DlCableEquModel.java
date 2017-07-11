package cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage;

import java.util.List;

import cn.droidlover.xdroidmvp.net.IModel;
import cn.droidlover.xdroidmvp.sys.model.BaseModel;

/**
 * Created by haoxi on 2017/7/11.
 */

public class DlCableEquModel extends BaseModel<List<DlCableEquModel.DlCableEqu>> implements IModel {

    public static class DlCableEqu {
        private Integer id;
        private String lineName;
        private String lineJName;
        private String personName;
        private String organizationByOrgIdName;
        private String organizationByPartyIdName;
        private String equPerson;
        private String areaUnder;
        private String setupDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getLineJName() {
            return lineJName;
        }

        public void setLineJName(String lineJName) {
            this.lineJName = lineJName;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getOrganizationByOrgIdName() {
            return organizationByOrgIdName;
        }

        public void setOrganizationByOrgIdName(String organizationByOrgIdName) {
            this.organizationByOrgIdName = organizationByOrgIdName;
        }

        public String getOrganizationByPartyIdName() {
            return organizationByPartyIdName;
        }

        public void setOrganizationByPartyIdName(String organizationByPartyIdName) {
            this.organizationByPartyIdName = organizationByPartyIdName;
        }

        public String getEquPerson() {
            return equPerson;
        }

        public void setEquPerson(String equPerson) {
            this.equPerson = equPerson;
        }

        public String getAreaUnder() {
            return areaUnder;
        }

        public void setAreaUnder(String areaUnder) {
            this.areaUnder = areaUnder;
        }

        public String getSetupDate() {
            return setupDate;
        }

        public void setSetupDate(String setupDate) {
            this.setupDate = setupDate;
        }
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
