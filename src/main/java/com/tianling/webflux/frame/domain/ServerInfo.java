package com.tianling.webflux.frame.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: TianLing
 * @Year: 2020
 * @DateTime: 2020/7/13 8:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    private String url;
}
