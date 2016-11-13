package br.com.alura.agenda.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by caique on 13/11/16.
 */

public class SMSReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu   (pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);
        if (dao.isAluno(telefone)) {
            Toast.makeText(context, "Chegou uma mensagem", Toast.LENGTH_LONG).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
            mediaPlayer.start();
        }
     }
}
