package org.molgenis.data.mysql;

import org.molgenis.MolgenisFieldTypes;
import org.molgenis.data.EditableEntityMetaData;
import org.molgenis.data.Entity;
import org.molgenis.data.EntityMetaData;
import org.molgenis.data.support.DefaultEntityMetaData;
import org.molgenis.data.support.MapEntity;

/** Test for MolgenisFieldTypes.DECIMAL */
public class MysqlRepositoryDecimalTest extends MysqlRepositoryAbstractDatatypeTest
{
	@Override
	public EntityMetaData createMetaData()
	{
		EditableEntityMetaData varcharMD = new DefaultEntityMetaData("DecimalTest")
				.setLabel("Decimal Test");
		varcharMD.setIdAttribute("col1");
		varcharMD.addAttribute("col1").setDataType(MolgenisFieldTypes.DECIMAL).setNillable(false);
		varcharMD.addAttribute("col2").setDataType(MolgenisFieldTypes.DECIMAL);
		varcharMD.addAttribute("col3").setDataType(MolgenisFieldTypes.DECIMAL).setDefaultValue(1.3);
		return varcharMD;
	}

	@Override
	public String createSql()
	{
		return "CREATE TABLE IF NOT EXISTS `DecimalTest`(`col1` DOUBLE NOT NULL, `col2` DOUBLE, `col3` DOUBLE, PRIMARY KEY (`col1`)) ENGINE=InnoDB;";
	}

	@Override
	public Entity defaultEntity()
	{
		Entity e = new MapEntity();
		e.set("col1", 2.9);
		e.set("col2", 3.1);
		return e;
	}
}
