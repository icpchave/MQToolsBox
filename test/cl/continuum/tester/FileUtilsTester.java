package cl.continuum.tester;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cl.continuum.mqtoolkit.model.QManagerData;
import cl.continuum.mqtoolkit.util.FileUtil;

public class FileUtilsTester {
	FileUtil fu = new FileUtil();
	List<QManagerData> qmgrs;
	
	
	public static void main(String[] args) {
		FileUtilsTester ft= new FileUtilsTester();
		
		try {
			ft.testWrite();
			ft.testRead();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testRead() throws FileNotFoundException, IOException{
		qmgrs = fu.readProperties();
		QManagerData qm;
		for (int i = 0; i < qmgrs.size(); i++) {
			qm = qmgrs.get(i);
			System.out.println("QMANAGER: "+ qm.getName() );
			System.out.println("HOST: "+ qm.getHost() );
			System.out.println("CHANNEL: "+ qm.getChannel() );
			System.out.println("PORT: "+ qm.getPort() );
		}
	}
	public void testWrite() throws FileNotFoundException, IOException{
		qmgrs = new ArrayList<QManagerData>();
		QManagerData qm1 = new QManagerData();
		QManagerData qm2 = new QManagerData();
		QManagerData qm3 = new QManagerData();
		
		qm1.setName("MILHOUSE_QM");
		qm1.setHost("localhost");
		qm1.setChannel("MQCONN");
		qm1.setPort(1414);
		
		qm2.setName("BART_QM");
		qm2.setHost("10.0.0.1");
		qm2.setChannel("SYSTEM.DEF.SVRCONN");
		qm2.setPort(2414);
		
		qm3.setName("HOMERO_QM");
		qm3.setHost("192.168.1.1");
		qm3.setChannel("SRVCONN");
		qm3.setPort(1424);
		
		qmgrs.add(qm1);
		qmgrs.add(qm2);
		qmgrs.add(qm3);
		fu.writeProperties(qmgrs);
	}
	
}
