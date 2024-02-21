package mx.com.desivecore.infraestructure.remissionEntry.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "remissions_entries_histories")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class RemissionEntryHistoryEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "remission_entry_id", unique = false, nullable = false)
	private Long remissionEntryId;

	@Column(name = "folio", unique = false, nullable = false, length = 20)
	private String folio;

	@Column(name = "action_date", unique = false, nullable = false)
	private Date actionDate;

	@Column(name = "action", unique = false, nullable = false, length = 30)
	private String action;

	@Column(name = "modified_data", unique = false, nullable = false, length = 500)
	private String modifiedData;

}
