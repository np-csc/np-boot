package cn.np.boots.rpc.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NpServiceBinding {

    SPI("spi");

    private String code;
}
