package com.andrew.printer.HF_RFID_Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Medical_sign_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Medical_sign();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Medical_sign()
      {
          /*设置打印机IP地址*/
    /*打印之前先确保打印机已配置到你所处的局域网内，可用ping命令检测打印机是否已经在局域网内
     *打印机IP地址可以打印自检页查看，也可以在Utility或者液晶屏中查看
     * 配置打印机的IP地址和端口号：可在打印机液晶屏或者Utility中配置
     * 端口一般不用配置，用默认值9100就好
     */
  
    String IPAddress = Printers.IPAddress;
    int port = Printers.NetPort;
	  
    /*
    *mm:点，毫米转为点，由打印机分辨率决定
    * 打印机分辨DPI=203时， mm=8
    * 打印机分辨DPI=300时， mm=12
    * 打印机分辨DPI=600时， mm=24
    */
    int mm = 12;

          int result = 0;
          String message = "";

             /*连接打印机*/
        result = PrintLab.PTK_Connect_Timer(IPAddress,port,2);
       if (result != 0) {
      	 System.err.println( Printers.getErrorInfo(result));
      	return;
       }

          /*函数返回值*/
          int result_printing = 0;
          /*横坐标偏移,设置标签整体横向移动位置*/
          int offset_X = 0;

          /*纵坐标偏移，设置标签整体纵向移动位置*/
          int offset_Y = 0;


          /*获取打印机状态*/
          int[] status_Net = new int[4];
          String PrinterStatus = "";
          result_printing = PrintLab.PTK_FeedBack();
          if (result_printing != 0) { Printers.showError("PTK_FeedBack", result_printing); return; }

          result_printing = PrintLab.PTK_ReadData(status_Net,4);
          if (result_printing != 0) { Printers.showError("PTK_ReadData", result_printing); return; }
       
          PrinterStatus = status_Net[0]+"";
         
           if (!PrinterStatus.equals("0") && !PrinterStatus.equals("4"))
          {
              PrintLab.PTK_CloseConnect();
              System.err.println("打印机未就绪，当前错误报告：" + Printers.Status_Explain(PrinterStatus));

              return;
          }


          /*清空缓存*/
          result_printing = PrintLab.PTK_ClearBuffer();
          if (result_printing != 0) { Printers.showError("PTK_ClearBuffer", result_printing); return; }

          /*设置打印黑度 取值范围 0-20*/
          result_printing = PrintLab.PTK_SetDarkness(10);
          if (result_printing != 0) { Printers.showError("PTK_SetDarkness", result_printing); return; }

          /*设置打印速度*/
          result_printing = PrintLab.PTK_SetPrintSpeed(4);
          if (result_printing != 0) { Printers.showError("PTK_SetPrintSpeed", result_printing); return; }

          /*设置打印方向*/
          result_printing = PrintLab.PTK_SetDirection('B');
          if (result_printing != 0) { Printers.showError("PTK_SetDirection", result_printing); return; }

          /*设置标签高度、间隙及偏移 */
          result_printing = PrintLab.PTK_SetLabelHeight(35 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(90 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印一维码*/
          result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 79), (int)(offset_Y + 102), 0, "1", 4, 12, 84, 'N', "2000002049");
          if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

          /*打印矩形*/
          result_printing = PrintLab.PTK_DrawRectangle((int)(offset_X + 27), (int)(offset_Y + 90), 4, 1036, 393);
          if (result_printing != 0) { Printers.showError("PTK_DrawRectangle", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 241), 1008, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 289), 1006, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 153), (int)(offset_Y + 197), 4, 193);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 471), (int)(offset_Y + 93), 4, 298);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 472), (int)(offset_Y + 142), 563, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 30), (int)(offset_Y + 195), 1004, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 612), (int)(offset_Y + 93), 4, 297);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 338), 1007, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印本地图形*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint(67, 32, "logo", ".\\logo\\logo.jpg", 0.8f, 92, 74, 0);
          if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 672), (int)(offset_Y + 97), 38, 0, "Arial", 1, 400, false, false, false, "2000002049");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 673), (int)(offset_Y + 147), 38, 0, "Arial", 1, 400, false, false, false, "血（4ml）");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 921), (int)(offset_Y + 147), 38, 0, "Arial", 1, 400, false, false, false, "黄管");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 217), (int)(offset_Y + 198), 38, 0, "Arial", 1, 400, false, false, false, "高大");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 217), (int)(offset_Y + 243), 38, 0, "Arial", 1, 400, false, false, false, "女 ");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 217), (int)(offset_Y + 294), 38, 0, "Arial", 1, 400, false, false, false, "64岁");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 217), (int)(offset_Y + 345), 38, 0, "Arial", 1, 400, false, false, false, "656845987");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 673), (int)(offset_Y + 245), 38, 0, "Arial", 1, 400, false, false, false, "骨科一病房");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 921), (int)(offset_Y + 248), 38, 0, "Arial", 1, 400, false, false, false, "11床");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 673), (int)(offset_Y + 295), 38, 0, "Arial", 1, 400, false, false, false, "2021-07-22");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 921), (int)(offset_Y + 294), 38, 0, "Arial", 1, 400, false, false, false, "07:00");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 673), (int)(offset_Y + 199), 38, 0, "Arial", 1, 400, false, false, false, "生化室（检验科）");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 673), (int)(offset_Y + 345), 38, 0, "Arial", 1, 400, false, false, false, "肝全，肾全");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 49), (int)(offset_Y + 198), 38, 0, "Arial", 1, 400, false, false, false, "姓名");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 49), (int)(offset_Y + 245), 38, 0, "Arial", 1, 400, false, false, false, "性别");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 49), (int)(offset_Y + 294), 38, 0, "Arial", 1, 400, false, false, false, "年龄");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 49), (int)(offset_Y + 343), 38, 0, "Arial", 1, 400, false, false, false, "ID号");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 200), 38, 0, "Arial", 1, 400, false, false, false, "科室");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 244), 38, 0, "Arial", 1, 400, false, false, false, "床号");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 295), 38, 0, "Arial", 1, 400, false, false, false, "日期");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 342), 38, 0, "Arial", 1, 400, false, false, false, "备注");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }
          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 391), (int)(offset_Y + 30), 49, 0, "楷体", 1, 400, false, false, false, "北京市阳光人民医院");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 97), 38, 0, "Arial", 1, 400, false, false, false, "编号");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 498), (int)(offset_Y + 150), 38, 0, "Arial", 1, 400, false, false, false, "品名");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*高频（HF） RFID打印设置*/
          result_printing = PrintLab.PTK_SetHFRFID('H', 0, 0);
          if (result_printing != 0) { Printers.showError("PTK_SetHFRFID", result_printing); return; }

          /*编码高频RFID数据，写数据之前确保RFID标签已经校准成功*/
          result_printing = PrintLab.PTK_RWHFLabel('W', 5, 1, "2000002049", false);
          if (result_printing != 0) { Printers.showError("PTK_RWHFLabel", result_printing); return; }

          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

         

          /*断开打印机连接*/
          PrintLab.PTK_CloseConnect();


          System.err.println("执行成功！");
      }
	  

}
