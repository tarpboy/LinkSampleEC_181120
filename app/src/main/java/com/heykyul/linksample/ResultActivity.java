package com.heykyul.linksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Set;

public class ResultActivity extends Activity {
	private TextView result;
	public String msg = "";
	public String trantype;
	public String resultcode;
	public Intent i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		result = (TextView)findViewById(R.id.TextView1);
		i = getIntent();
		printInent(i);

//		msg = "";
//		msg += "TRAN_NO = ";
//		msg += i.getStringExtra("TRAN_NO");
//		msg += "\nTRAN_TYPE = ";
//		msg += i.getStringExtra("TRAN_TYPE");
//		msg += "\nCARD_NUM = ";
//		msg += i.getStringExtra("CARD_NUM");
//		msg += "\nCARD_NAME = ";
//		msg += i.getStringExtra("CARD_NAME");
//		msg += "\nTOTAL_AMOUNT = ";
//		msg += i.getStringExtra("TOTAL_AMOUNT");
//		msg += "\nTAX = ";
//		msg += i.getStringExtra("TAX");
//		msg += "\nTIP = ";
//		msg += i.getStringExtra("TIP");
//		msg += "\nINSTALLMENT = ";
//		msg += i.getStringExtra("INSTALLMENT");
//		msg += "\nRESULT_CODE = ";
//		msg += i.getStringExtra("RESULT_CODE");
//		msg += "\nRESULT_MSG = ";
//		msg += i.getStringExtra("RESULT_MSG");
//		msg += "\nAPPROVAL_NUM = ";
//		msg += i.getStringExtra("APPROVAL_NUM");
//		msg += "\nAPPROVAL_DATE =";
//		msg += i.getStringExtra("APPROVAL_DATE");
//		msg += "\nACQUIRER_CODE =";
//		msg += i.getStringExtra("ACQUIRER_CODE");
//		msg += "\nACQUIRER_NAME =";
//		msg += i.getStringExtra("ACQUIRER_NAME");
//		msg += "\nORDER_NUM =";
//		msg += i.getStringExtra("ORDER_NUM");
//		msg += "\nMERCHANT_NUM =";
//		msg += i.getStringExtra("MERCHANT_NUM");
//		msg += "\nSHOP_TID =";
//		msg += i.getStringExtra("SHOP_TID");
//		msg += "\nADD_FIELD =";
//		msg += i.getStringExtra("ADD_FIELD");
//		msg += "\nNOTICE =";
//		msg += i.getStringExtra("NOTICE");
//		result.setText(msg);
	
	}

	public void printInent(Intent i) {
		try {
			//Log.e("KTC","-------------------------------------------------------");
			//util.saveLog("-------------------------------------------------------");
			if (i != null) {
				Bundle extras = i.getExtras();
				if (extras != null) {
					Set keys = extras.keySet();

					for (String _key : extras.keySet()) {
						Log.e("RESULTACT","key=" + _key + " : " + extras.get(_key));
						msg += "\n" + _key + "=";
						msg += extras.get(_key);
					}
				}
				result.setText(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	public void onBackPressed() {
		finish();
	}

}
