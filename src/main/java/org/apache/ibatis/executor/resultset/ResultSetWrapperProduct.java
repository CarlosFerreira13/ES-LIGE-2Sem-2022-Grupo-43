package org.apache.ibatis.executor.resultset;


import java.util.Map;
import java.util.List;
import java.util.HashMap;
import org.apache.ibatis.mapping.ResultMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class ResultSetWrapperProduct {
	private final Map<String, List<String>> mappedColumnNamesMap = new HashMap<>();
	private final Map<String, List<String>> unMappedColumnNamesMap = new HashMap<>();

	public List<String> getMappedColumnNames(ResultMap resultMap, String columnPrefix, List<String> thisColumnNames,
			ResultSetWrapper resultSetWrapper) throws SQLException {
		List<String> mappedColumnNames = mappedColumnNamesMap.get(getMapKey(resultMap, columnPrefix));
		if (mappedColumnNames == null) {
			loadMappedAndUnmappedColumnNames(resultMap, columnPrefix, thisColumnNames, resultSetWrapper);
			mappedColumnNames = mappedColumnNamesMap.get(getMapKey(resultMap, columnPrefix));
		}
		return mappedColumnNames;
	}

	public void loadMappedAndUnmappedColumnNames(ResultMap resultMap, String columnPrefix, List<String> thisColumnNames,
			ResultSetWrapper resultSetWrapper) throws SQLException {
		List<String> mappedColumnNames = new ArrayList<>();
		List<String> unmappedColumnNames = new ArrayList<>();
		final String upperColumnPrefix = columnPrefix == null ? null : columnPrefix.toUpperCase(Locale.ENGLISH);
		final Set<String> mappedColumns = resultSetWrapper.prependPrefixes(resultMap.getMappedColumns(),
				upperColumnPrefix);
		for (String columnName : thisColumnNames) {
			final String upperColumnName = columnName.toUpperCase(Locale.ENGLISH);
			if (mappedColumns.contains(upperColumnName)) {
				mappedColumnNames.add(upperColumnName);
			} else {
				unmappedColumnNames.add(columnName);
			}
		}
		mappedColumnNamesMap.put(getMapKey(resultMap, columnPrefix), mappedColumnNames);
		unMappedColumnNamesMap.put(getMapKey(resultMap, columnPrefix), unmappedColumnNames);
	}

	public List<String> getUnmappedColumnNames(ResultMap resultMap, String columnPrefix, List<String> thisColumnNames,
			ResultSetWrapper resultSetWrapper) throws SQLException {
		List<String> unMappedColumnNames = unMappedColumnNamesMap.get(getMapKey(resultMap, columnPrefix));
		if (unMappedColumnNames == null) {
			loadMappedAndUnmappedColumnNames(resultMap, columnPrefix, thisColumnNames, resultSetWrapper);
			unMappedColumnNames = unMappedColumnNamesMap.get(getMapKey(resultMap, columnPrefix));
		}
		return unMappedColumnNames;
	}

	public String getMapKey(ResultMap resultMap, String columnPrefix) {
		return resultMap.getId() + ":" + columnPrefix;
	}
}