package tech.hiphone.cms.domain.common;

import tech.hiphone.cms.domain.id.TargetMaterialExtId;

// 替换 AbstractTargetMaterial
public interface ITargetMaterial<T, TID> {

    TID getTargetId();

    TargetMaterialExtId<T> getId();

    void setId(TargetMaterialExtId<T> id);

    MaterialInfo getMaterialInfo();

    void setMaterialInfo(MaterialInfo materialInfo);

}
