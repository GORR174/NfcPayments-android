package net.catstack.nfcpay.common.aliases

import net.catstack.nfcpay.common.server.ServerResponseModel
import retrofit2.Response

typealias ServerResponse<T> = Response<ServerResponseModel<T>>