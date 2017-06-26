package com.brilapps.etl.source;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SourceNetworkHeaderExtractor {

	static Logger logger = Logger.getLogger(SourceNetworkHeaderExtractor.class);

	public ArrayList<String> getColumnHeaders(final Sheet sheet) {
		logger.debug(" entering getColumnHeaders() ");
		ArrayList<String> headers = new ArrayList<String>();
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				headers.add(currentCell.getStringCellValue());
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) {
		ArrayList<String> headers = getColumnHeaders(sheet);
		SourceNetworkHeaderColumnHeaders[] sourceNetworkHeaderColumnHeaders = SourceNetworkHeaderColumnHeaders.values();
		for (SourceNetworkHeaderColumnHeaders sourceNetworkHeaderColumnHeader : sourceNetworkHeaderColumnHeaders) {
			if (!headers.contains(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
				logger.error(ProjectDefinitionExtractor.class,
						new Exception("Column " + sourceNetworkHeaderColumnHeader.getColumnHeader()
								+ " missing in source Network Header file."));
			}
		}
		return headers;
	}

}
