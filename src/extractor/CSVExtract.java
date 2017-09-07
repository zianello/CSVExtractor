package extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import azureDL.db.DataLakeTable;
import azureDL.db.util.DataLakeTableUtilImpl;

public class CSVExtract {
	
	public static final String FILE_PATH = "";
	public static int counter = 0;
	
	public static void main(String[] args) throws IOException {
//		String folderName = args[0];
		String folderName = "Soccer";
		
		try{
			List<File> fileList = listFilesForFolder(new File(folderName));
			ArrayList<DataLakeTable> list = new ArrayList<DataLakeTable>();
			for(File f : fileList){
				list.addAll(getCSVElements(folderName + "//" + f.getName()));
				System.out.println(folderName + "//" + f.getName());
			}
			saveCSVElements(list);
			}
		catch(NullPointerException e){};
	}
	
	public static List<DataLakeTable> getCSVElements(String filePath) throws IOException{
		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<DataLakeTable> list = new ArrayList<DataLakeTable>();
		String cLine;
		while ((cLine = br.readLine()) != null) {
			counter++;
			list.add(new DataLakeTable(counter, cLine.split(";")));
		}
		br.close();
		return list;
	}
	
	public static void saveCSVElements(List<DataLakeTable> list){
		DataLakeTableUtilImpl dlu = new DataLakeTableUtilImpl();
		dlu.saveObjectList(list);
		dlu.closeConnection();
	}
	
	public static ArrayList<File> listFilesForFolder(final File folder){
		ArrayList<File> fileList = new ArrayList<File>();
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory())
	            listFilesForFolder(fileEntry);
	        else 
	         fileList.add(fileEntry);
	    }
		return fileList;
	}
	
}
