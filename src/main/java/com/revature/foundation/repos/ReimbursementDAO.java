package com.revature.foundation.repos;

import com.revature.foundation.models.Reimbursement;
import com.revature.foundation.models.ReimbursementStatus;
import com.revature.foundation.models.ReimbursementType;
import com.revature.foundation.util.exceptions.DataSourceException;
import com.revature.foundation.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// implements CrudDAO <Reimbursement>
public class ReimbursementDAO {

//    private final String rootSelect = "SELECT " +
//            "ers.reimb_id, ers.amount, ers.desciption, ers.author_id, ers.resolver_id, ers.status_id, ers.type_id, rs.status, rt.type " +
//            "FROM ers_reimbursements ers " +
//            "JOIN reimbursement_statuses rs " +
//            "ON ers.status_id = rs.status_id " +
//            "JOIN reimbursement_types rt " +
//            "ON ers.type_id = rt.type_id ";
//
//    @Override
//    public void save(Reimbursement newReimbursement) {
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            conn.setAutoCommit(false);
//            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursements (reimb_id, amount, desciption, author_id, resolver_id, status_id, type_id, submitted)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
//            pstmt.setString(1, newReimbursement.getId());
//            pstmt.setDouble(2, newReimbursement.getAmount());
//            pstmt.setString(3, newReimbursement.getDescription());
//            pstmt.setString(4, newReimbursement.getAuthor_id());
//            pstmt.setString(5, newReimbursement.getResolver_id());
//            pstmt.setString(6, newReimbursement.getReimbursementStatus().getId());
//            pstmt.setString(7, newReimbursement.getReimbursementType().getId());
//            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
//
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 1) {
//                conn.rollback();
//                throw new ResourcePersistenceException("Failed to persist user to data source");
//            }
//
//            conn.commit();
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//    }
//
//    @Override
//    public Reimbursement getById(String id) {
//        Reimbursement reimb = null;
//
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE reimb_id = ?");
//            pstmt.setString(1, id);
//
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                reimb = new Reimbursement();
//                reimb.setId(rs.getString("reimb_id"));
//                reimb.setAmount(rs.getDouble("amount"));
//                reimb.setDescription(rs.getString("desciption"));
//                reimb.setAuthor_id(rs.getString("author_id"));
//                reimb.setResolver_id(rs.getString("resolver_id"));
//                reimb.setReimbursementStatus(new ReimbursementStatus((rs.getString("status_id")),rs.getString("status")));
//                reimb.setReimbursementType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
//            }
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//        return reimb;
//    }
//
//    public List<Reimbursement> getByType(String id) {
//        List<Reimbursement> reimbursements = new ArrayList<>();
//
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE ers.type_id = ?");
//            pstmt.setString(1, id);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Reimbursement reimb = new Reimbursement();
//                reimb.setId(rs.getString("reimb_id"));
//                reimb.setAmount(rs.getDouble("amount"));
//                reimb.setDescription(rs.getString("desciption"));
//                reimb.setAuthor_id(rs.getString("author_id"));
//                reimb.setResolver_id(rs.getString("resolver_id"));
//                reimb.setReimbursementStatus(new ReimbursementStatus((rs.getString("status_id")),rs.getString("status")));
//                reimb.setReimbursementType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
//                reimbursements.add(reimb);
//            }
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//        return reimbursements;
//    }
//
//    public List<Reimbursement> getByStatus(String id) {
//        List<Reimbursement> reimbursements = new ArrayList<>();
//
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE ers.status_id = ?");
//            pstmt.setString(1, id);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Reimbursement reimb = new Reimbursement();
//                reimb.setId(rs.getString("reimb_id"));
//                reimb.setAmount(rs.getDouble("amount"));
//                reimb.setDescription(rs.getString("desciption"));
//                reimb.setAuthor_id(rs.getString("author_id"));
//                reimb.setResolver_id(rs.getString("resolver_id"));
//                reimb.setReimbursementStatus(new ReimbursementStatus((rs.getString("status_id")),rs.getString("status")));
//                reimb.setReimbursementType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
//                reimbursements.add(reimb);
//            }
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//        return reimbursements;
//    }
//
//    public List<Reimbursement> getByAuthor(String id) {
//        List<Reimbursement> reimbursements = new ArrayList<>();
//
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE ers.author_id = ?");
//            pstmt.setString(1, id);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Reimbursement reimb = new Reimbursement();
//                reimb.setId(rs.getString("reimb_id"));
//                reimb.setAmount(rs.getDouble("amount"));
//                reimb.setDescription(rs.getString("desciption"));
//                reimb.setAuthor_id(rs.getString("author_id"));
//                reimb.setResolver_id(rs.getString("resolver_id"));
//                reimb.setReimbursementStatus(new ReimbursementStatus((rs.getString("status_id")),rs.getString("status")));
//                reimb.setReimbursementType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
//                reimbursements.add(reimb);
//            }
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//        return reimbursements;
//    }
//
//    @Override
//    public List<Reimbursement> getAll() {
//        List<Reimbursement> reimbursements = new ArrayList<>();
//
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
//            while (rs.next()) {
//                Reimbursement reimb = new Reimbursement();
//                reimb.setId(rs.getString("reimb_id"));
//                reimb.setAmount(rs.getDouble("amount"));
//                reimb.setDescription(rs.getString("desciption"));
//                reimb.setAuthor_id(rs.getString("author_id"));
//                reimb.setResolver_id(rs.getString("resolver_id"));
//                reimb.setReimbursementStatus(new ReimbursementStatus((rs.getString("status_id")),rs.getString("status")));
//                reimb.setReimbursementType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
//                reimbursements.add(reimb);
//            }
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//        return reimbursements;
//    }
//
//    @Override
//    public void update(Reimbursement updateReimbursement) {
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            conn.setAutoCommit(false);
//            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
//                    "SET amount = ?, " +
//                    "desciption = ?, " +
//                    "type_id = ?, "+
//                    "status_id = ? "+
//                    "WHERE reimb_id = ?");
//            pstmt.setDouble(1, updateReimbursement.getAmount());
//            pstmt.setString(2, updateReimbursement.getDescription());
//            pstmt.setString(3,updateReimbursement.getReimbursementType().getId());
//            pstmt.setString(4,updateReimbursement.getReimbursementStatus().getId());
//            pstmt.setString(5, updateReimbursement.getId());
//
//
//            // TODO allow role to be updated as well
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 1) {
//                throw new ResourcePersistenceException("Failed to update reimbursement data within datasource.");
//            }
//
//            conn.commit();
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//    }
//
//    public void update_status(Reimbursement updateReimbursement){
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            conn.setAutoCommit(false);
//            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
//                    "SET status_id = ?, "+
//                    "resolved = ? "+
//                    "WHERE reimb_id = ?");
//            pstmt.setString(1,updateReimbursement.getReimbursementStatus().getId());
//            pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
//            pstmt.setString(3, updateReimbursement.getId());
//
//
//            // TODO allow role to be updated as well
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 1) {
//                throw new ResourcePersistenceException("Failed to update reimbursement data within datasource.");
//            }
//
//            conn.commit();
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//    }
//
//    public void update_type(Reimbursement updateReimbursement){
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            conn.setAutoCommit(false);
//            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
//                    "SET type_id = ?, "+
//                    "resolved = ? "+
//                    "WHERE reimb_id = ?");
//            pstmt.setString(1,updateReimbursement.getReimbursementType().getId());
//            pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
//            pstmt.setString(3, updateReimbursement.getId());
//
//
//            // TODO allow role to be updated as well
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 1) {
//                throw new ResourcePersistenceException("Failed to update reimbursement data within datasource.");
//            }
//
//            conn.commit();
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//    }
//
//    @Override
//    public void deleteById(String id) {
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            conn.setAutoCommit(false);
//            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ers_reimbursements WHERE reimb_id = ?");
//            pstmt.setString(1, id);
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 1) {
//                conn.rollback();
//                throw new ResourcePersistenceException("Failed to delete user from data source");
//            }
//
//            conn.commit();
//
//        } catch (SQLException e) {
//            throw new DataSourceException(e);
//        }
//
//    }
}
