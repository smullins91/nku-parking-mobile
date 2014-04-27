<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LotReport.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.LotReport" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>NKU Parking Management System</title>
    <script src="../../Scripts/jquery-2.1.0.js"></script>
    <script src="../../Scripts/bootstrap2.js"></script>
    <link href="../../Content/bootstrap2.2.css" rel="stylesheet" />
    <style>
         
         body { background: #FFF url('/../Images/background.jpg') repeat-x; width:975px; margin-left:auto;
                margin-right:auto
         }
         
         
    </style>
</head>
<body>


    <form id="form1" runat="server">
    <div class="header-image" style="margin-left:auto; margin-right:auto; width:970px; ">
             <asp:Image ID="Banner" runat="server" ImageUrl="~/Images/banner.jpg"/>
         </div>
          <div style="margin-top:10px">
             
               <a href="../UsersManagement/UsersManagement.aspx" style="float:right">Main Menu</a>
               <h4>NKU Parking Management System</h4>
           </div>

    <div>
        <h2 style="text-align:center">Parking Lot Report</h2>
        <asp:GridView ID="gvLots" runat="server" AutoGenerateColumns="False" HorizontalAlign="Center" Width="100%" DataKeyNames="lotNumber" CssClass="table table-hover table-striped" >
                                 <Columns>
                                 <asp:BoundField DataField="lotNumber" HeaderText="Lot Name" />
                                 <asp:BoundField DataField="typeString" HeaderText="Designation" />
                                 <asp:BoundField DataField="totalSpaces" HeaderText="Number of Spaces" />
                                 <asp:BoundField DataField="available" HeaderText="Available Spaces" />
                                 <asp:BoundField DataField="percentUsed" HeaderText="Percent Used" />

                             </Columns>
                         </asp:GridView>
                         <asp:SqlDataSource ID="SqlDataSource1" runat="server"></asp:SqlDataSource>
                         <asp:ObjectDataSource ID="AllLots" runat="server"></asp:ObjectDataSource>
    </div>
    </form>
</body>
</html>
