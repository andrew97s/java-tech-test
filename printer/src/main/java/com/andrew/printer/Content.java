package com.andrew.printer;

import com.andrew.printer.GeneralLabel.*;
import com.andrew.printer.HF_RFID.EncodeHFRFID_Net;
import com.andrew.printer.HF_RFID.EncodeHFRFID_USB;
import com.andrew.printer.HF_RFID.ReadHFRFIDData_Net;
import com.andrew.printer.HF_RFID.ReadHFRFIDData_USB;
import com.andrew.printer.HF_RFID_Template_Case.*;
import com.andrew.printer.Other.GetPrinterStatus_Net;
import com.andrew.printer.Other.GetPrinterStatus_USB;
import com.andrew.printer.Other.PrintToFile;
import com.andrew.printer.Template_Case.*;
import com.andrew.printer.Template_General.*;
import com.andrew.printer.UHF_RFID.*;
import com.andrew.printer.UHF_RFID_Template_Case.*;
import com.andrew.printer.UHF_RFID_Template_General.*;

public class Content {

	public static void main(String[] args) {
		Print_USB printUsb = new Print_USB();
		Print_Text_USB printTextUsb = new Print_Text_USB();
	}
	
	/*打印函数数据到文件*/
	PrintToFile printToFile=new PrintToFile();
	//Print_USB 通过USB端口打印
	//Print_Net 通过网络端口打印
	
	public static class Print_USB{

		/*通用打印*/
			/* 打印文字 */
			Print_Text_USB print_Text_USB=new Print_Text_USB();
			
			/* 打印条码 */
			PrintBarcode_USB printBarcode_USB=new PrintBarcode_USB();
			
			/* 打印Base64图形 */
			PrintGraphics_Base64_USB printGraphics_Base64_USB=new PrintGraphics_Base64_USB();
			
			/* 打印图形 */
			PrintGraphics_USB printGraphics_USB=new PrintGraphics_USB();
			
			/*多台打印机*/
			Print_MultiPrinters_USB print_MultiPrinters_USB=new Print_MultiPrinters_USB();
			
		/*其他*/
			/*获取打印机状态*/
			GetPrinterStatus_USB getPrinterStatus_USB=new GetPrinterStatus_USB();
			
		
		/*超高频RFID （UHF RFID）*/
			/*写数据*/
			EncodeUHFRFID_USB encodeUHFRFID_USB=new EncodeUHFRFID_USB(); 
			
			/*读数据*/
			ReadUHFRFIDData_USB readUHFRFIDData_USB=new ReadUHFRFIDData_USB();
			
			/*写数据+锁定*/
			EncodeAndlock_UHF_USB encodeAndlock_UHF_USB=new EncodeAndlock_UHF_USB();
			
			/*解锁+写数据+锁定*/
			Unlock_Encode_Lock_UHF_USB unlock_Encode_Lock_UHF_USB=new Unlock_Encode_Lock_UHF_USB();
			
			
			/*高频RFID （HF RFID）*/
			/*写数据*/
			EncodeHFRFID_USB encodeHFRFID_USB=new EncodeHFRFID_USB(); 
			
			/*读数据*/
			ReadHFRFIDData_USB readHFRFIDData_USB=new ReadHFRFIDData_USB();
			
		/*条码标签模板*/
			/*单张打印*/
			PrintOneLabel_USB printOneLabel_USB=new PrintOneLabel_USB();
			
			/*批量打印*/
			PrintAJob_USB printAJob_USB=new PrintAJob_USB(); 
			
			/*多列标签打印*/
			PrintLabels_MultiCol_USB printLabels_MultiCol_USB=new PrintLabels_MultiCol_USB();
			
			
			
		/*超高频RFID(UHF_RFID)标签模板*/
			/*批量写RFID和打印*/
			EncodeRFID_Print_Job_USB encodeRFID_Print_Job_USB=new EncodeRFID_Print_Job_USB();
			
			/*批量先读RFID再打印*/
			ReadFirst_EncodeRFID_Print_job_USB readFirst_EncodeRFID_Print_job_USB=new ReadFirst_EncodeRFID_Print_job_USB();
			
			/*批量超高频RFID写读打印*/
			EncodeRFID_Read_Print_job_USB encodeRFID_Read_Print_job_USB=new EncodeRFID_Read_Print_job_USB();
			
			/*单张打印-打印完成返回RFID数据*/
			Print_ResturnRFIDData_USB print_ResturnRFIDData_USB=new Print_ResturnRFIDData_USB(); 
			
			
			
		/*应用场景条码标签模板*/
			/*洗水唛*/
			WashLabel_USB washLabel_USB=new WashLabel_USB();
			
			/*固定资产*/
			Fixed_Assets_USB fixed_Assets_USB=new Fixed_Assets_USB();
			
			/*服装吊牌*/
			Clothing_tag_USB clothing_tag_USB=new Clothing_tag_USB();
			
			/*商品价格标签*/
			Price_Tag_USB price_Tag_USB=new Price_Tag_USB();
			
			/*电子产品机身标*/
			Electronic_Product_body_label_USB electronic_Product_body_label_USB=new Electronic_Product_body_label_USB();
			
			/*电子产品外箱标*/
			Electronic_Product_Box_Label_USB electronic_Product_Box_Label_USB=new Electronic_Product_Box_Label_USB();
			
			/*带表格的铭牌标签*/
			NameplateLabelWithForm_USB nameplateLabelWithForm_USB=new NameplateLabelWithForm_USB();
			
			
		/*应用场景超高频RFID标签模板*/
			/*出入证*/
			Pass_USB pass_USB=new Pass_USB();
			
			/*固定资产*/
			Fixed_Assets_RFID_USB fixed_Assets_RFID_USB=new Fixed_Assets_RFID_USB();
			
			/*服装吊牌*/
			Clothing_Tag_RFID_USB clothing_Tag_RFID_USB=new Clothing_Tag_RFID_USB();
			
			/*珠宝吊牌*/
			Jewelry_Tag_RFID_USB jewelry_Tag_RFID_USB=new Jewelry_Tag_RFID_USB();
			
			/*腕带打印*/
			Wristband_RFID_USB wristband_RFID_USB=new Wristband_RFID_USB();
			
			
		/*应用场景高频RFID标签模板*/
			
			/*医疗标识标签*/
			Medical_sign_USB medical_sign_USB=new Medical_sign_USB();
			
			/*高频RFID 批量先读后写+打印*/
			ReadFirst_EncodeHFRFID_Print_job_USB readFirst_EncodeHFRFID_Print_job_USB=new ReadFirst_EncodeHFRFID_Print_job_USB();
			
			/*高频写RFID并返回RFID数据*/
			EndcodeHFRFID_ReturnRFIDData_USB endcodeHFRFID_ReturnRFIDData_USB=new  EndcodeHFRFID_ReturnRFIDData_USB();
		
	}
	
	
	public class Print_Net{
	/*通用打印*/
		/* 打印文字 */
		Print_Text_Net print_Text_Net=new Print_Text_Net();
		
		/* 打印条码 */
		PrintBarcode_Net printBarcode_Net=new PrintBarcode_Net();
		
		/* 打印Base64图形 */
		PrintGraphics_Base64_Net printGraphics_Base64_Net=new PrintGraphics_Base64_Net();
		
		/* 打印图形 */
		PrintGraphics_Net printGraphics_Net=new PrintGraphics_Net();
		
		
	/*其他*/
		/*获取打印机状态*/
		GetPrinterStatus_Net getPrinterStatus_Net=new GetPrinterStatus_Net();
		
	
	/*超高频RFID （UHF RFID）*/
		/*写数据*/
		EncodeUHFRFID_Net encodeUHFRFID_Net=new EncodeUHFRFID_Net(); 
		
		/*读数据*/
		ReadUHFRFIDData_Net readUHFRFIDData_Net=new ReadUHFRFIDData_Net();
		
		/*写数据+锁定*/
		EncodeAndlock_UHF_Net encodeAndlock_UHF_Net=new EncodeAndlock_UHF_Net();
		
		/*解锁+写数据+锁定*/
		Unlock_Encode_Lock_UHF_Net unlock_Encode_Lock_UHF_Net=new Unlock_Encode_Lock_UHF_Net();
		
		
		/*高频RFID （HF RFID）*/
		/*写数据*/
		EncodeHFRFID_Net encodeHFRFID_Net=new EncodeHFRFID_Net(); 
		
		/*读数据*/
		ReadHFRFIDData_Net readHFRFIDData_Net=new ReadHFRFIDData_Net();
		
	/*条码标签模板*/
		/*单张打印*/
		PrintOneLabel_Net printOneLabel_Net=new PrintOneLabel_Net();
		
		/*批量打印*/
		PrintAJob_Net printAJob_Net=new PrintAJob_Net(); 
		
		/*多列标签打印*/
		PrintLabels_MultiCol_Net printLabels_MultiCol_Net=new PrintLabels_MultiCol_Net();
		
		
		
	/*超高频RFID(UHF_RFID)标签模板*/
		/*批量写RFID和打印*/
		EncodeRFID_Print_Job_Net encodeRFID_Print_Job_Net=new EncodeRFID_Print_Job_Net();
		
		/*批量先读RFID再打印*/
		ReadFirst_EncodeRFID_Print_job_Net readFirst_EncodeRFID_Print_job_Net=new ReadFirst_EncodeRFID_Print_job_Net();
		
		/*批量超高频RFID写读打印*/
		EncodeRFID_Read_Print_job_Net encodeRFID_Read_Print_job_Net=new EncodeRFID_Read_Print_job_Net();
		
		/*单张打印-打印完成返回RFID数据*/
		Print_ResturnRFIDData_Net print_ResturnRFIDData_Net=new Print_ResturnRFIDData_Net(); 
		
		
		
	/*应用场景条码标签模板*/
		/*洗水唛*/
		WashLabel_Net washLabel_Net=new WashLabel_Net();
		
		/*固定资产*/
		Fixed_Assets_Net fixed_Assets_Net=new Fixed_Assets_Net();
		
		/*服装吊牌*/
		Clothing_tag_Net clothing_tag_Net=new Clothing_tag_Net();
		
		/*商品价格标签*/
		Price_Tag_Net price_Tag_Net=new Price_Tag_Net();
		
		/*电子产品机身标*/
		Electronic_Product_body_label_Net electronic_Product_body_label_Net=new Electronic_Product_body_label_Net();
		
		/*电子产品外箱标*/
		Electronic_Product_Box_Label_Net electronic_Product_Box_Label_Net=new Electronic_Product_Box_Label_Net();
		
		/*带表格的铭牌标签*/
		NameplateLabelWithForm_Net nameplateLabelWithForm_Net=new NameplateLabelWithForm_Net();
		
		
	/*应用场景超高频RFID标签模板*/
		/*出入证*/
		Pass_Net pass_Net=new Pass_Net();
		
		/*固定资产*/
		Fixed_Assets_RFID_Net fixed_Assets_RFID_Net=new Fixed_Assets_RFID_Net();
		
		/*服装吊牌*/
		Clothing_Tag_RFID_Net clothing_Tag_RFID_Net=new Clothing_Tag_RFID_Net();
		
		/*珠宝吊牌*/
		Jewelry_Tag_RFID_Net jewelry_Tag_RFID_Net=new Jewelry_Tag_RFID_Net();
		
		/*腕带打印*/
		Wristband_RFID_Net wristband_RFID_Net=new Wristband_RFID_Net();
		
		
	/*应用场景高频RFID标签模板*/
		
		/*医疗标识标签*/
		Medical_sign_Net medical_sign_Net=new Medical_sign_Net();
		
		/*高频RFID 批量先读后写+打印*/
		ReadFirst_EncodeHFRFID_Print_job_Net readFirst_EncodeHFRFID_Print_job_Net=new ReadFirst_EncodeHFRFID_Print_job_Net();
		
		/*高频写RFID并返回RFID数据*/
		EndcodeHFRFID_ReturnRFIDData_Net endcodeHFRFID_ReturnRFIDData_Net=new  EndcodeHFRFID_ReturnRFIDData_Net();
	}
	
}


