package com.jiazhuo.blockgamesquare.qo;

import com.jiazhuo.blockgamesquare.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GameQueryObject extends QueryObject {
    protected Byte type ; //游戏类型 1:ETH 2:EOS 3:右上角活动
    private Byte status; //游戏状态
}
