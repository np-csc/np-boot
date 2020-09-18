package cn.np.boots.rpc.core.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NpServiceBinding {

    SPI("spi"),
    SOFA("sofa");

    private String code;
}
