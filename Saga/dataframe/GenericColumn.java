package dataframe;

public class GenericColumn extends Column{

		public GenericColumn(String name) {
			super(name);
		}
		public GenericColumn(String name, char type) {
			super(name,type);
		}
		public GenericColumn(Column column_byIndex) {
			super(column_byIndex);
		}
 
}
