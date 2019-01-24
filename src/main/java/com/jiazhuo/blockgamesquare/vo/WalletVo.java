package com.jiazhuo.blockgamesquare.vo;

import com.jiazhuo.blockgamesquare.domain.EOSTOKEN;
import com.jiazhuo.blockgamesquare.domain.ETHTOKEN;
import com.jiazhuo.blockgamesquare.domain.UserBasic;
import lombok.*;

import java.io.Serializable;

/**
 * 钱包信息
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class WalletVo implements Serializable {
    private ETHTOKEN bgstoken; //bgstoken信息

    private ETHTOKEN ethtoken; //ethtoken信息

    private EOSTOKEN eostoken; //eostoken信息

    private UserBasic userBasic; //用户信息
}
