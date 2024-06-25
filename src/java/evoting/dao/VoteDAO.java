 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;
import static evoting.dao.CandidateDAO.getUserNameById;
import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author hp
 */
public class VoteDAO {
   private String userId;
   public static PreparedStatement ps1;
   public static PreparedStatement ps2;
   public static PreparedStatement ps3;
   static {
       try
       {
           ps1=DBConnection.getConnection().prepareStatement("Select candidate_id from voting where voter_id=?");
           ps2=DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.candidate_id=?");
           ps3=DBConnection.getConnection().prepareStatement("Insert into voting values(?,?)");
   }
       catch(SQLException ex)
       {
           ex.printStackTrace();
       }
}
   public static String getCandidateId(String userid) throws SQLException
   {
      ps1.setString(1, userid);
      ResultSet rs =ps1.executeQuery();
      if(rs.next()){
          return rs.getString(1);
      }
      return null;  
   }
   public static CandidateInfo getVote(String candidateid) throws Exception
   {      
      ps2.setString(1,candidateid);
     ResultSet rs = ps2.executeQuery();
     CandidateInfo cd = new CandidateInfo();
     // First convert Image from binary to Base64 format
     // Blob-->InputStream-->char array--->Base64
     Blob blob;
     InputStream inputStream;
     byte [] buffer;
     byte [] imageBytes;
     int bytesRead;
     String base64Image;
     ByteArrayOutputStream outputStream;
     if(rs.next())
     {
        blob =rs.getBlob(4);
        inputStream=blob.getBinaryStream();
         outputStream=new ByteArrayOutputStream();
         // outpt stream k pass method h jo 4096 size ka array(Byte) leta h 
         // Method name-write();
         buffer=new byte[4096];
         bytesRead=-1;
         
         while((bytesRead=inputStream.read(buffer))!=-1){
// inputstream.read(buffer) copy krega image array ko 4096 wli array mein
          outputStream.write(buffer, 0, bytesRead);
     }
       imageBytes=outputStream.toByteArray();
// toByteArray() outputstream ka data byte  k array mein return krke dega         
// Encoder class will convert imageBytes array into BASE64 format     
    Base64.Encoder en = Base64.getEncoder();
// encodeToString will convert byte array to BASE64     
   base64Image =  en.encodeToString(imageBytes);
    cd.setSymbol(base64Image);
    cd.setCandidateId(candidateid) ;
    cd.setCandidateName(rs.getString(2));
    cd.setParty(rs.getString(3));
    
 }
     return cd;   
   }
   
  public static boolean addVote(VoteDTO obj) throws SQLException
  {
      ps3.setString(1,obj.getCandidateId());
      ps3.setString(2,obj.getVoterId());
      return (ps3.executeUpdate()!=0);
  }
   
}  