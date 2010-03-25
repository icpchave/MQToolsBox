package cl.continuum.mqtoolkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;

import cl.continuum.mqtoolkit.model.QManagerData;

public class FileUtil {
	public static final String _HOST = "HOST";
	public static final String _CHANNEL = "CHANNEL";
	public static final String _PORT = "PORT";
	public static final String _QMNAMES = "QMNAMES";

	public void writeProperties(List<QManagerData> qmgrs)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		String qmNames = "";
		QManagerData qm;
		for (int i = 0; i < qmgrs.size(); i++) {
			qm = qmgrs.get(i);
			if (qmNames.equalsIgnoreCase("")) {
				qmNames = qm.getName();
				System.out.println("sin coma" + qmNames);
			} else {
				qmNames = qmNames + "," + qm.getName();
				System.out.println("Con coma:" + qmNames);
			}
			properties.setProperty(qm.getName() + "." + FileUtil._HOST, qm
					.getHost());
			properties.setProperty(qm.getName() + "." + FileUtil._CHANNEL, qm
					.getChannel());
			properties.setProperty(qm.getName() + "." + FileUtil._PORT, String
					.valueOf(qm.getPort()));
		}
		System.out.println("QMNAMES=" + qmNames);
		properties.setProperty(FileUtil._QMNAMES, qmNames);
		properties.store(new FileOutputStream("conf/conf.properties"), null);

	}

	public List<QManagerData> readProperties() throws FileNotFoundException,
			IOException {
		String[] qmgrsNames;
		List<QManagerData> qmgrs = new ArrayList<QManagerData>();
		QManagerData qmgr;
		String qmname;
		// Leyendo archivo de propiedades
		Properties properties = new Properties();
		properties.load(new FileInputStream("conf/conf.properties"));
		qmgrsNames = properties.getProperty(FileUtil._QMNAMES).split(",");

		for (int i = 0; i < qmgrsNames.length; i++) {
			qmgr = new QManagerData();
			qmname = qmgrsNames[i];
			qmgr.setName(qmname);
			qmgr.setChannel(properties.getProperty(qmname + "."
					+ FileUtil._CHANNEL));
			qmgr.setHost(properties.getProperty(qmname + "." + FileUtil._HOST));
			qmgr.setPort(Integer.parseInt(properties.getProperty(qmname + "."
					+ FileUtil._PORT)));
			qmgrs.add(qmgr);
		}

		return qmgrs;
	}

	public QManagerData readSelectedQM(String qmName)
			throws FileNotFoundException, IOException {
		String[] qmgrsNames;
		QManagerData qmData = new QManagerData();
		Properties properties = new Properties();
		properties.load(new FileInputStream("conf/conf.properties"));
		qmgrsNames = properties.getProperty(FileUtil._QMNAMES).split(",");

		qmData.setName(qmName);
		qmData.setChannel(properties.getProperty(qmName + "."
				+ FileUtil._CHANNEL));
		qmData.setHost(properties.getProperty(qmName + "." + FileUtil._HOST));
		qmData.setPort(Integer.parseInt(properties.getProperty(qmName + "."
				+ FileUtil._PORT)));

		return qmData;
	}

	public void saveQManager(QManagerData qmData) throws FileNotFoundException,
			IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("conf/conf.properties"));
		String qmNames = "";

		if (properties.getProperty(FileUtil._QMNAMES) == null
				|| properties.getProperty(FileUtil._QMNAMES).trim().isEmpty()) {
			properties.setProperty(FileUtil._QMNAMES, qmData.getName());
		} else {
			properties.setProperty(FileUtil._QMNAMES, properties
					.getProperty(FileUtil._QMNAMES)
					+ "," + qmData.getName());
		}

		properties.setProperty(qmData.getName() + "." + FileUtil._HOST, qmData
				.getHost());
		properties.setProperty(qmData.getName() + "." + FileUtil._CHANNEL,
				qmData.getChannel());
		properties.setProperty(qmData.getName() + "." + FileUtil._PORT, String
				.valueOf(qmData.getPort()));
		properties.store(new FileOutputStream("conf/conf.properties"), null);

	}
	
	public void removeQM(String qmName) throws FileNotFoundException, IOException{
		Properties properties = new Properties();
		String[] qmNamesOld;
		String qmNamesNew = new String();
		properties.load(new FileInputStream("conf/conf.properties"));
		properties.remove(qmName+"."+FileUtil._CHANNEL);
		properties.remove(qmName+"."+FileUtil._HOST);
		properties.remove(qmName+"."+FileUtil._PORT);
		qmNamesOld = properties.getProperty(FileUtil._QMNAMES).split(",");
		for (int i = 0; i < qmNamesOld.length; i++) {
			if (!qmNamesOld[i].equalsIgnoreCase(qmName)) {
				if (qmNamesNew.isEmpty()) {
					qmNamesNew = qmNamesNew + qmNamesOld[i];
				} else {
					qmNamesNew = qmNamesNew + "," +qmNamesOld[i];
				}
			}
		}
		properties.setProperty(FileUtil._QMNAMES, qmNamesNew);
		properties.store(new FileOutputStream("conf/conf.properties"), null);
	}



}
