package com.acongfly.studyjava.utils;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

@Data
public class SimpleEntity implements Serializable {

    @NotBlank(message = "名字不能为空或者空串", groups = {ValidatorInterfaceColl.account.class})
    @Length(min = 2, max = 10, message = "名字必须由2~10个字组成")
    private String name;

    @Past(message = "时间不能晚于当前时间")
    private Date date;

    @Email(message = "邮箱格式不正确", groups = {ValidatorInterfaceColl.settle.class})
    private String email;

    @Pattern(regexp = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,10}", message = "密码必须是5~10位数字和字母的组合")
    private String password;

    @AssertTrue(message = "字段必须为真", groups = {ValidatorInterfaceColl.settle.class})
    private boolean valid;

    @Min(1)
    @NotNull
    private Long amt;

    @EnumValid(target = AccountStatusEnum.class, groups = {Default.class}, enumMethod = "getValue")
    private int type;


}

