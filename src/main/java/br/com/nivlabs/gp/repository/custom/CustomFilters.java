package br.com.nivlabs.gp.repository.custom;

import java.io.Serializable;

/**
 * Classe base para a criação de filtros de consultas
 * 
 * @author viniciosarodrigues
 *
 */
public class CustomFilters implements Serializable {

    private static final long serialVersionUID = 8995632262865503148L;

    private Integer page = 0;
    private Integer size = 24;
    private String orderBy = "id";
    private String direction = "ASC";

    public CustomFilters() {
        super();
    }

    public CustomFilters(Integer page, Integer size, String orderBy, String direction) {
        super();
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.direction = direction;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomFilters other = (CustomFilters) obj;
        if (direction == null) {
            if (other.direction != null)
                return false;
        } else if (!direction.equals(other.direction))
            return false;
        if (orderBy == null) {
            if (other.orderBy != null)
                return false;
        } else if (!orderBy.equals(other.orderBy))
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomFilters [page=" + page + ", size=" + size + ", orderBy=" + orderBy + ", direction=" + direction + "]";
    }

}
