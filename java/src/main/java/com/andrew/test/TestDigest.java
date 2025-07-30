package com.andrew.test;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author tongwenjin
 * @since 2025-2-14
 */
public class TestDigest {
    public static void main(String[] args) {
        // 用户凭据
        String username = "admin";
        String password = "123456";
        String realm = "PO2tnwX1MoBMRp0F";

        // 服务器提供的参数
        String nonce = "4Ju05uXb4jhYR1Pb";  // 服务器随机数
        String uri = "/ROC/System/capabilities";
        String method = "GET";    // HTTP 请求方法
        String qop = "auth";      // 认证质量保护
        String nc = "00000001";   // 请求计数器
        String cnonce = "client_salt"; // 客户端随机数

        // 计算 Digest 认证的 response
        String response = calculateDigestResponse(username, password, realm, nonce, uri, method, qop, nc, cnonce);

        System.out.println( response);
    }

    public static String calculateDigestResponse(String username, String password, String realm, String nonce,
                                                 String uri, String method, String qop, String nc, String cnonce) {
        // 计算 HA1 = MD5(username:realm:password)
        String ha1 = DigestUtil.md5Hex(username + ":" + realm + ":" + password);

        // 计算 HA2 = MD5(method:uri)
        String ha2 = DigestUtil.md5Hex(method + ":" + uri);

        // 计算 response = MD5(HA1:nonce:nc:cnonce:qop:HA2)
        return DigestUtil.md5Hex(ha1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + ha2);
    }
}
