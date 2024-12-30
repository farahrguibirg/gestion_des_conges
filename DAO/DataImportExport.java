package DAO;

import java.io.IOException;
import java.util.List;

public interface DataImportExport<T> {
	void importData(String fileName)throws IOException;
	void exportData(String fileName,List<T> data)throws IOException;

}
