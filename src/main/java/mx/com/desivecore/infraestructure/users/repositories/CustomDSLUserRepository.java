package mx.com.desivecore.infraestructure.users.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;
import mx.com.desivecore.infraestructure.users.entities.QUserEntity;

@Log
@Repository
public class CustomDSLUserRepository extends QuerydslRepositorySupport {

	public CustomDSLUserRepository() {
		super(UserModel.class);
	}

	@Autowired
	EntityManager em;

	public List<UserModel> findUserListByParams(UserSearchParams userSearchParams) {
		log.info("INIT findUserListByParams()");

		JPAQuery<UserModel> query = new JPAQuery<>(em);

		QUserEntity user = QUserEntity.userEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(user);

		/**
		 * Start evaluation of search parameters
		 */
		if (userSearchParams.getEmail() != null) {
			if (!userSearchParams.getEmail().isEmpty()) {
				query.where(user.email.eq(userSearchParams.getEmail()));
			}
		}

		if (userSearchParams.getBranchId() != null) {
			query.where(user.branchId.eq(userSearchParams.getBranchId()));
		}

		if (userSearchParams.getName() != null) {
			if (!userSearchParams.getName().isEmpty()) {
				String fullName = userSearchParams.getName().replace(' ', '%');
				query.where(user.name.like("%" + fullName + "%").or(user.firstSurname.like("%" + fullName + "%"))
						.or(user.secondSurname.like("%" + fullName + "%")));

			}
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(UserModel.class, user.userId, user.branchId, user.name, user.firstSurname,
				user.secondSurname, user.email, user.status));

		return query.orderBy(user.firstSurname.asc()).fetch();
	}
}
