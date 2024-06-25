/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import evoting.dto.UpdateCandidateDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 *
 * @author hp
 */
public class CandidateDAO {

    private static PreparedStatement ps, ps1, ps2, ps3, ps4, ps5, ps6;
    private static Statement st;

    static {
        try {
            st = DBConnection.getConnection().createStatement();
            ps = DBConnection.getConnection().prepareStatement("Select count(*) from candidate");
            ps1 = DBConnection.getConnection().prepareStatement("Select username from user_details where adhar_no=?");
            ps2 = DBConnection.getConnection().prepareStatement("Select distinct city from user_details");
            ps3 = DBConnection.getConnection().prepareStatement("Insert into candidate values(?,?,?,?,?)");
            ps4 = DBConnection.getConnection().prepareStatement("Select * from candidate where candidate_id=?");
            ps5 = DBConnection.getConnection().prepareStatement("update candidate set party=?,symbol=?,city=? where candidate_id=?");
            ps6 = DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.city=(select city from user_details where adhar_no=?)");

        } catch (SQLException ex) {
            System.out.println("Error In DB comm:" + ex);

        }
    }

    public static String getNewId() throws SQLException {
        ResultSet rs = ps.executeQuery();
// aise bhi likh skte h            
//         if(rs.next()){
//             return "C"+(100+rs.getInt(1)+1); 
//         }
//         else{ // ye kbhi nhi chlega  
//             return "C101";
//         }
        rs.next();
        return "C" + (100 + rs.getInt(1) + 1);
    }

    public static String getUserNameById(String uid) throws SQLException {
        ps1.setString(1, uid);
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        } else {
            return null;
        }
    }

    public static ArrayList<String> getCity() throws SQLException {
        ArrayList<String> cityList = new ArrayList<>();
        ResultSet rs = ps2.executeQuery();
        while (rs.next()) {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }

    public static boolean addCandidate(CandidateDTO obj) throws SQLException {
        ps3.setString(1, obj.getCandidateId());
        ps3.setString(2, obj.getParty());
        ps3.setString(3, obj.getUserid());
        ps3.setBinaryStream(4, obj.getSymbol());
        ps3.setString(5, obj.getCity());
        return ps3.executeUpdate() != 0;
    }

    public static boolean updateCandidate(UpdateCandidateDTO obj) throws SQLException {
        ps5.setString(1, obj.getParty());
        ps5.setString(2, obj.getCity());
        ps5.setBinaryStream(3, obj.getSymbol());
        ps5.setString(4, obj.getCandidateid());
        return ps5.executeUpdate()==1;
    }

    public static ArrayList<String> getCandidateId() throws SQLException {
        ArrayList<String> candidateIdList = new ArrayList<>();
        ResultSet rs = st.executeQuery("Select candidate_id from candidate");

        while (rs.next()) {
            candidateIdList.add(rs.getString(1));
        }
        return candidateIdList;
    }

    public static CandidateDetails getDetailsById(String cid) throws Exception {
        ps4.setString(1, cid);
        ResultSet rs = ps4.executeQuery();
        CandidateDetails cd = new CandidateDetails();
        // First convert Image from binary to Base64 format
        // Blob-->InputStream-->char array--->Base64
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        if (rs.next()) {
            blob = rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream = new ByteArrayOutputStream();
            // outpt stream k pass method h jo 4096 size ka array(Byte) leta h 
            // Method name-write();
            buffer = new byte[4096];
            bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
// inputstream.read(buffer) copy krega image array ko 4096 wli array mein
                outputStream.write(buffer, 0, bytesRead);
            }
            imageBytes = outputStream.toByteArray();
// toByteArray() outputstream ka data byte  k array mein return krke dega         
// Encoder class will convert imageBytes array into BASE64 format     
            Encoder en = Base64.getEncoder();
// encodeToString will convert byte array to BASE64     
            base64Image = en.encodeToString(imageBytes);
            cd.setSymbol(base64Image);
            cd.setCandidateId(cid);
            cd.setCandidateName(getUserNameById(rs.getString(3)));
            cd.setParty(rs.getString(2));
            cd.setCity(rs.getString(5));
            cd.setUserId(rs.getString(3));
        }
        return cd;
    }

    public static ArrayList<CandidateInfo> viewCandidate(String adhar_id) throws Exception {
        ArrayList<CandidateInfo> candidateList = new ArrayList<CandidateInfo>();
        ps6.setString(1,adhar_id);
        ResultSet rs = ps6.executeQuery();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        System.out.println("In view candidate");
        while(rs.next()) {
                blob = rs.getBlob(4);
                inputStream = blob.getBinaryStream();
                outputStream = new ByteArrayOutputStream();  
                buffer = new byte[4096];
                bytesRead = -1;

                while((bytesRead = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                imageBytes = outputStream.toByteArray();
                Encoder en = Base64.getEncoder();
                base64Image = en.encodeToString(imageBytes);
                CandidateInfo cd = new CandidateInfo();
                cd.setSymbol(base64Image);
                cd.setCandidateId(rs.getString(1)) ;
                cd.setCandidateName(rs.getString(2));
                cd.setParty(rs.getString(3));
                candidateList.add(cd);
                System.out.println("Candidate details from loop :"+cd);
           
            }
        
            return candidateList;  
        }
    }
  