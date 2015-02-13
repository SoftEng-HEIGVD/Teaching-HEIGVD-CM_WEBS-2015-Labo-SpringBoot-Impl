package ch.heigvd.ptl.sc.rest.params;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.HeaderParam;
import org.springframework.data.domain.Sort;

public class PagerAndSorterParams {
	private int page = 0;
	private int size = 10;
	private final List<Sort.Order> sort = new ArrayList<>();

	public PagerAndSorterParams(
		@HeaderParam("x-pagination") String pagination, 
		@HeaderParam("x-sort") String sort) {
		
		if (pagination != null && !"".equals(pagination)) {
			String[] paginationElems = pagination.split(";");
			
			page = Integer.parseInt(paginationElems[0]);
			size = Integer.parseInt(paginationElems[1]);
		}
		
		if (sort != null && !"".equals(sort)) {
			String[] sortElements = sort.split(" ");
			
			for (String sortElement : sortElements) {
				if (sortElement.startsWith("-")) {
					this.sort.add(new Sort.Order(Sort.Direction.DESC, sort.substring(1)));
				}
				else {
					this.sort.add(new Sort.Order(Sort.Direction.ASC, sort));
				}
			}
		}
		else {
			this.sort.add(new Sort.Order("id"));
		}
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public List<Sort.Order> getSort() {
		return sort;
	}
}
