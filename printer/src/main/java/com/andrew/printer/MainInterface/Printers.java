package com.andrew.printer.MainInterface;

import java.io.UnsupportedEncodingException;

public class Printers {
	// String IPAddress = "192.168.1.168"
	public static String IPAddress = "192.168.1.27";
	public static int NetPort = 9100;
	static CDFPSKDll PrintLab = CDFPSKDll.Instance;
	public static void showError(String Name, int ErrorCode) {
		System.err.println(Name + ":" + getErrorInfo(ErrorCode));
		PrintLab.ClosePort();
		PrintLab.PTK_CloseTextPort();
		PrintLab.PTK_CloseUSBPort();
		PrintLab.PTK_CloseConnect();
	}

	// 解析打印机状态
	public static String Status_Explain(String PrinterStatus) {

		int status_t = Integer.parseInt(PrinterStatus.replace("W10", ""));

		String status = "";
		switch (status_t) {
		case 0:
			status = "无错误";
			break;
		case 1:
			status = "语法错误";
			break;
		case 4:
			status = "正在打印中";
			break;
		case 82:
			status = "碳带检测出错";
			break;
		case 83:
			status = "标签检测出错";
			break;
		case 86:
			status = "切刀检测出错";
			break;
		case 87:
			status = "打印头未关闭";
			break;
		case 88:
			status = "暂停状态";
			break;
		case 108:
			status = "设置RFID写数据的方式和内容区域执行失败，输入参数错误";
			break;
		case 109:
			status = "RFID标签写入数据失败，已达到重试次数";
			break;
		case 110:
			status = "写入RFID数据失败，但未超过重试次数";
			break;
		case 111:
			status = "RFID标签校准失败";
			break;
		case 112:
			status = "设置RFID读取数据的方式和内容区域执行失败，输入参数错误";
			break;
		case 116:
			status = "读取RFID标签数据失败";
			break;

		default:
			status = "未知错误";
			break;
		}

		return status;
	}

	public static String getErrorInfo(int errorCode) {

		String information = "";
		byte[] errorInfo = new byte[2048];
		int result = PrintLab.PTK_GetErrorInfo(errorCode, errorInfo, 2048);
		if (result == 0) {

			try {
				information = new String(errorInfo, "gbk").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (result == -1) {
			information = "解析错误代码失败，没有该错误代码";
		} else if (result == 4) {
			information = "错误信息过长无法显示";
		} else {
			information = "无法解析错误解析码返回值：" + result;
		}

		return information;
	}
}
