package com.andrew.printer.MainInterface;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface CDFPSKDll extends Library {

	// 第一步，根据JDK版本重命名CDFPSDK.dll，将X86目录下的Release目录下的dll重命名为CDFPSDK32.dll，将X64目录下的Release目录下的dll重命名为CDFPSDK64.dll
	// 放在src目录下
	String DLLName = "CDFPSK" + System.getProperty("sun.arch.data.model");
	@SuppressWarnings("deprecation")
	CDFPSKDll Instance = (CDFPSKDll) Native.loadLibrary(DLLName, CDFPSKDll.class);
	int PTK_GetLastError();

	int PTK_WSAGetLastError();

	/*
	 * 日志功能 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_OpenLogMode(String filePath);

	int PTK_CloseLogMode();

	/*
	 * 编码转换功能 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_UTF8toGBK(byte[] utf8Str, byte[] gbkStr, int gbkStrCount);

	int PTK_GBKtoUTF8(byte[] gbkStr, byte[] utf8Str, int utf8StrCount);

	/*
	 * 端口操作 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_OpenUSBPort(int port);

	int PTK_CloseUSBPort();

	int PTK_Connect_Timer(String IPAddress, int Port, int time);

	int PTK_Connect(String IPAddress, int Port);

	int PTK_CloseConnect();

	int PTK_OpenSerialPort(int port, int bRate);

	int PTK_CloseSerialPort();

	int PTK_OpenPrinter(String printerName);

	int PTK_ClosePrinter();

	int PTK_OpenParallelPort(int port);

	int PTK_CloseParallelPort();

	int PTK_OpenTextPort(String fn);

	int PTK_CloseTextPort();

	int PTK_OpenUSBPort_Buff(int portNum);

	int PTK_Connect_Timer_Buff(String IPAddr, int netPort, int time_sec);

	int PTK_OpenPrinter_Buff(String printerName);

	int PTK_CloseBuffPort();

	int PTK_WriteBuffToPrinter();

	int PTK_SendFile(String FilePath);

	int PTK_SendCmd(String data, int datalen);

	int PTK_SendString(int charset, String data);

	int PTK_GetUSBID(byte[] p);

	int PTK_GetErrorInfo(int error_n, byte[] errorInfo, int infoSize);

	int PTK_GetAllPrinterUSBInfo(byte[] USBInfo, int infoSize);

	int PTK_GetInfo();

	int PTK_GetErrState();

	/*
	 * 打印机设置 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_PrintConfiguration();

	int PTK_MediaDetect();

	int PTK_UserFeed(int feedLen);

	int PTK_UserBackFeed(int feedLen);

	int PTK_EnableFLASH();

	int PTK_DisableFLASH();

	int PTK_FeedMedia();

	int PTK_CutPage(int page);

	int PTK_CutPageEx(int page);

	int PTK_SetCoordinateOrigin(int px, int py);

	int PTK_GetUtilityInfo(int infoNum, byte[] data, int dataSize);

	int PTK_GetAllPrinterInfo(int infoNum, int fileflag, byte[] data, int dataSize);

	int PTK_ErrorReport_USBInterrupt(int[] status);

	int PTK_GetPrinterName(byte[] PrinterName);

	int PTK_GetPrinterDPI(int[] dpi);

	int PTK_GetPrinterKey_USB(byte[] printerKey);

	/*
	 * 标签设置 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_ClearBuffer();

	int PTK_SetPrintSpeed(int px);

	int PTK_SetDirection(char direct);

	int PTK_SetDarkness(int id);

	int PTK_SetLabelHeight(int lheight, int gapH, int gapOffset, boolean bFlag);

	int PTK_SetLabelWidth(int lwidth);

	int PTK_PrintLabel(int number, int cpnumber);

	int PTK_PrintLabelFeedback(byte[] data, int dataSize);

	/*
	 * 打印文字 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_DrawText(int px, int py, int pdirec, int pFont, int pHorizontal, int pVertical, char ptext, String pstr);

	int PTK_DrawTextEx(int px, int py, int pdirec, int pFont, int pHorizontal, int pVertical, char ptext, String pstr,
			int varible);

	int PTK_DrawTextTrueTypeW(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight, int FItalic,
			int FUnline, int FStrikeOut, String id_name, String data);

	int PTK_DrawText_TrueType(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight, boolean b,
			boolean c, boolean d, String data);

	int PTK_DrawText_TrueType_AutoFeedLine(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight,
			boolean FItalic, boolean FUnline, boolean FStrikeOut, int lineMaxWidth, int lineMaxNum, int lineGapH,
			char middleSwitch, char codeFormat, String data);

	int PTK_DrawText_TrueTypeEx(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight,
			int FItalic, int FUnline, int FStrikeOut, int lineMaxWidth, int lineMaxNum, int lineGapH, int middleSwitch,
			String data);

	/*
	 * 打印图片 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_ChangeIMGtoPCX(String filename);

	int PTK_PcxGraphicsList();

	int PTK_PcxGraphicsDel(String pid);

	int PTK_AnyGraphicsDownload(String pcxname, String filename, float ratio, int width, int height, int iDire);

	int PTK_DrawPcxGraphics(int px, int py, String gname);

	int PTK_AnyGraphicsPrint(int x, int y, String GraphicsName, String filePath, float ratio, int width, int height,
			int iDire);

	int PTK_AnyGraphicsPrint_Base64(int px, int py, int imageType, float ratio, int width, int height, int iDire,
			String imageBuffer);

	int PTK_AnyGraphicsDownloadFromMemory(String pcxname, int imageType, int imageSize, float ratio, int width,
			int height, int iDire, Byte[] imageBuffer);

	int PTK_AnyGraphicsPrintFromMemory(int px, int py, String pcxname, int imageType, int imageSize, float ratio,
			int width, int height, int iDire, Byte[] imageBuffer);

	int PTK_PcxGraphicsDownload(String pcxname, String pcxpath);

	int PTK_PrintPCX(int px, int py, String filename);

	int PTK_BmpGraphicsDownload(String pcxname, String pcxpath, int iDire);

	/*
	 * 打印点阵图片 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_BinGraphicsList();

	int PTK_BinGraphicsDel(String pid);

	int PTK_BinGraphicsDownload(String name, int pbyte, int pH, byte[] Gdata);

	int PTK_RecallBinGraphics(int px, int py, String name);

	int PTK_DrawBinGraphics(int px, int py, int pbyte, int pH, byte[] Gdata);

	/*
	 * 打印线条 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_DrawRectangle(int px, int py, int thickness, int pEx, int pEy);

	int PTK_DrawLineXor(int px, int py, int pbyte, int pH);

	int PTK_DrawLineOr(int px, int py, int pLength, int pH);

	int PTK_DrawDiagonal(int px, int py, int thickness, int pEx, int pEy);

	int PTK_DrawWhiteLine(int px, int py, int plength, int pH);

	/*
	 * 打印二维码 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_DrawBar2D_QR(int x, int y, int w, int v, int o, int r, int m, int g, int s, String pstr);

	int PTK_DrawBar2D_QREx(int x, int y, int o, int r, int g, int s, int v, String id_name, String pstr);

	int PTK_DrawBar2D_HANXIN(int x, int y, int w, int v, int o, int r, int m, int g, int s, String pstr);

	int PTK_DrawBar2D_Pdf417(int x, int y, int w, int v, int s, int c, int px, int py, int r, int l, int t, int o,
			String pstr);

	int PTK_DrawBar2D_Pdf417Ex(int x, int y, int w, int v, int s, int c, int px, int py, int r, int l, int t, int o,
			String pstr);

	int PTK_DrawBar2D_MaxiCode(int x, int y, int m, int u, String pstr);

	int PTK_DrawBar2D_DATAMATRIX(int x, int y, int w, int h, int o, int m, String pstr);

	/*
	 * 打印一维码 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_DrawBarcode(int px, int py, int pdirec, String pCode, int pHorizontal, int pVertical, int pbright,
			char ptext, String pstr);

	int PTK_DrawBarcodeEx(int px, int py, int pdirec, String pCode, int NarrowWidth, int pHorizontal, int pVertical,
			int pbright, char ptext, String pstr, int Varible); // 打印一个条码。

	/*
	 * 打印表单及相关 =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_GetStorageList(byte[] tempName, int listBuffSize, int TempType);

	int PTK_FormList();

	int PTK_FormDel(String pid);

	int PTK_FormDownload(String pid);

	int PTK_FormEnd();

	int PTK_ExecForm(String pid);

	int PTK_DefineVariable(int pid, int pmax, char porder, String pmsg);

	int PTK_DefineCounter(int id, int maxNum, char ptext, String pstr, String pMsg);

	int PTK_Download();

	int PTK_DownloadInitVar(String pstr);

	int PTK_PrintLabelAuto(int number, int cpnumber);

	int PTK_FormPrinting(String FormName, String FormVariable, int Quantity, int Copies);

	/*
	 * 超高频RFID标签读写及相关（UHF） =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_RFIDCalibrate();

	int PTK_RWRFIDLabel(int nRWMode, int nWForm, int nStartBlock, int nWDataNum, int nWArea, String pstr);

	int PTK_RWRFIDLabelEx(int nRWMode, int nWForm, int nStartBlock, int nWDataNum, int nWArea, String pstr);

	int PTK_SetRFLabelPWAndLockRFLabel(int nOperationMode, int OperationnArea, String pstr);

	int PTK_EncodeRFIDPC(String PCValue);

	int PTK_SetRFID(int nReservationParameters, int nReadWriteLocation, int ReadWriteArea, int nMaxErrNum,
			int nErrProcessingMethod);

	int PTK_ReadRFIDLabelData(int nDataBlock, int nRFPower, int bFeed, byte[] data, int dataSize);

	int PTK_ReadRFIDLabelDataEx(int nDataBlock, int nRFPower, int bFeed, String AccessCode, byte[] data, int dataSize);

	int PTK_RFIDEndPrintLabel(int block, byte[] data, int dataSize);

	int PTK_RFIDEndPrintLabelFeedBack(int block, byte[] data, int dataSize, byte[] printerStatus, int statusSize);

	int PTK_SetReadRFIDForwardSpeed(int speed);

	int PTK_SetReadRFIDBackSpeed(int speed);

	int PTK_ReadRFIDSetting(int nRMode, int nStartBlock, int nRBlockLength, String AccessCode);

	int PTK_PrintAndCallback(byte[] data, byte[] printerStatus);

	/*
	 * 高频RFID标签读写及相关（HF） =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int PTK_RWHFLabel(char ptext, int nStartBlock, int nBlockNum, String pstr, boolean b);

	int PTK_SetHFRFID(char pWForm, int nProtocolType, int nMaxErrNumd);

	int PTK_ReadHFTagData(int wPort, int rPort, int BaudRate, Boolean HandShake, int TimeOut, int nStartBlock,
			int nBlockNum, char pFeed, Pointer strRFData);

	int PTK_ReadHFLabelData(int nStartBlock, int nBlockNum, char pFeed, byte[] data, int dataSize);

	int PTK_ReadHFLabeUID(char pFeed, byte[] data, int dataSize);

	int PTK_ReadHFTagUID(int wPort, int rPort, int BaudRate, Boolean HandShake, int TimeOut, char pFeed,
			Pointer strRFData);

	int PTK_ReadHFTagDataPrintAuto(int nStartBlock, int nBlockNum);

	int PTK_ReadHFTagUIDPrintAuto();

	int PTK_SetHFAFI(int nAFIValue);

	int PTK_SetHFDSFID(int nDSFIDValue);

	int PTK_SetHFEAS(char EAS);

	int PTK_HFDecrypt(int key, int nStartBlock, int nBlockNum, String VerifyPassword);

	int PTK_LockHFLabel(int nStartBlock, int nBlockNum, String keyA, String keyB, String nControlByte);

	int PTK_LockHFIdentifier(char Identifier);

	int PTK_LockHFBlock(int nStartBlock, int nBlockNum);

	int PTK_SetHFKey(int lockType, String keyA, String keyB, String keyFx);

	int PTK_SetHFCRCCommand(int lockType, String oldCRCCommand, String newCRCCommand);

	int PTK_SetHFPrivateCommand(int lockType, String oldPrivateCommand, String newPrivateCommand);

	int PTK_LockHFUser(int lockType, int nStartBlock, int nBlockNum);

	int PTK_SetHFCFG10(String CFG_Set_0x10);

	int PTK_SetHFCFG80(String CFG_Set_0x80);

	int PTK_ReadHFRFIDSetting(int nDataBlock, int nStartBlock, int nRBlockLength);

	/*
	 * 兼容版API =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=
	 * =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^= =^-^=/
	 */
	int OpenPort(int px);

	int ClosePort();

	int SetPCComPort(int BaudRate, boolean HandShake);

	int PTK_Reset();

	int PTK_SoftFontList();

	int PTK_SoftFontDel(char pid);

	int PTK_DisableBackFeed();

	int PTK_EnableBackFeed(int distance);

	int PTK_SetPrinterState(String state);

	int PTK_DisableErrorReport();

	int PTK_EnableErrorReport();

	int PTK_SetRFIDLabelRetryCount(int nRetryCount);

	int PTK_FeedBack();

	int PTK_ReadData(int[] data, int dataSize);

	int PTK_ErrorReport(int wPort, int rPort, int BaudRate, int HandShake, int TimeOut);

	int PTK_ErrorReportNet(String PrintIPAddress, int PrintetPort);

	int PTK_ErrorReportUSB(int USBport);

	int PTK_SetFontGap(int gap);

	int PTK_SetBarCodeFontName(char Name, int FontW, int FontH);

	int PTK_RenameDownloadFont(int StoreType, char Fontname, String DownloadFontName);

	int PTK_SetCharSets(int BitValue, char CharSets, String CountryCode);

	int PTK_ReadRFTagDataNet(String IPAddress, int Port, int nFeedbackPort, int nRFPower, int bFeed, Pointer strRFData);

	int PTK_ReadRFTagDataUSB(int usbPort, int nDataBlock, int nRFPower, int bFeed, byte[] b);

	int PTK_ClearUIDBuffers();

	int PTK_ReadHFTagUIDUSB(int usbPort, char pFeed, byte[] readbuffer);

	int PTK_SetTearAndTphOffset(float tear_offset, float tph_offset);

	

}
