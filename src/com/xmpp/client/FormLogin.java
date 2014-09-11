package com.xmpp.client;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.xmpp.client.util.XmppTool;
import com.xmpp.client.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FormLogin extends Activity implements OnClickListener 
{

	private EditText useridText, pwdText;
	private LinearLayout layout1, layout2;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formlogin);

		//��ȡ�û�������
		this.useridText = (EditText) findViewById(R.id.formlogin_userid);
		this.pwdText = (EditText) findViewById(R.id.formlogin_pwd);

		//���ڵ�¼
		this.layout1 = (LinearLayout) findViewById(R.id.formlogin_layout1);
		//��¼����
		this.layout2 = (LinearLayout) findViewById(R.id.formlogin_layout2);

		Button btsave = (Button) findViewById(R.id.formlogin_btsubmit);
		btsave.setOnClickListener(this);
		Button btcancel = (Button) findViewById(R.id.formlogin_btcancel);
		btcancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		//����ID�������ύ����ȡ��
		switch (v.getId()) {
		case R.id.formlogin_btsubmit:
			//ȡ��������û�������
			final String USERID = this.useridText.getText().toString();
			final String PWD = this.pwdText.getText().toString();
		
			
			Thread t=new Thread(new Runnable() {				
				public void run() {
					//sendEmptyMessage:����һ����Ϣ
					handler.sendEmptyMessage(1);
					try {
						//����
						//XmppTool.getConnection().login(USERID, PWD);
						//XmppTool.getConnection().login("6535285", "123456");
						XmppTool.getConnection().login("3333333", "123456");
//						Log.i("XMPPClient", "Logged in as " + XmppTool.getConnection().getUser());
						
						//״̬
						Presence presence = new Presence(Presence.Type.available);
						XmppTool.getConnection().sendPacket(presence);
						
						Intent intent = new Intent();
						intent.setClass(FormLogin.this, FormClient.class);
						intent.putExtra("USERID", USERID);
						FormLogin.this.startActivity(intent);
						FormLogin.this.finish();
					}
					catch (XMPPException e) 
					{
						XmppTool.closeConnection();
						
						handler.sendEmptyMessage(2);
					}					
				}
			});
			t.start();
			break;
		case R.id.formlogin_btcancel:
			finish();
			break;
		}
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg)
		{
			
			if(msg.what==1)
			{
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
			}
			else if(msg.what==2)
			{
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				Toast.makeText(FormLogin.this, "��¼ʧ�ܣ�",Toast.LENGTH_SHORT).show();
			}
		};
	};
}