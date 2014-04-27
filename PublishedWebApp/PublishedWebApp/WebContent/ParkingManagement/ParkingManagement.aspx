<%@ Page Language="C#" AutoEventWireup="true"  MasterPageFile="~/Site.Master" CodeBehind="ParkingManagement.aspx.cs" Inherits="ParkingManagement.WebContent.ParkingManagement.ParkingManagement" %>

      


<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
    <section class="featured" style="height:600px">



        <script type="text/javascript"
      src="http://maps.google.com/maps/api/js?sensor=false">
    </script>
    <script type="text/javascript">



        function initialize() {
            var mapOptions = {
                center: new google.maps.LatLng(39.03149, -84.46408),
                zoom: 16
            };
            var map = new google.maps.Map(document.getElementById("map-canvas"),
                mapOptions);


            var tempLot = new Array();
            //VERSION FOR FINAL PROGRAM
            //using LotCollection, which is the JSON that i receive from codebehind correctly
            var totalLotHolder = []; //dont think this is needed, but maybe
            for (var i = 0; i < LotCollection.length; i++)
            {
                tempLot[i] = new Array();
                for (var j = 0; j < LotCollection[i].points.length; j++)
                {
                    tempLot[i].push(new google.maps.LatLng(LotCollection[i].points[j].lat, LotCollection[i].points[j].lng)); //make a coordinate point and push onto list
                }
            }
        


            for (var i = 0; i < LotCollection.length; i++){
                totalLotHolder[i] = new google.maps.Polygon({
                    paths: tempLot[i],      //NOTICE THE i INDEX
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#FF0000',
                    fillOpacity: 0.35
                });
                totalLotHolder[i].setMap(map)
            }

            
            var lotBCoords = [
                new google.maps.LatLng(39.03198, -84.46733),
                new google.maps.LatLng(39.03221, -84.46927),
                new google.maps.LatLng(39.03113, -84.46970),
                new google.maps.LatLng(39.03083, -84.46731),
            ];


            /*
            //Construct the polygon for lotB
            lotB = new google.maps.Polygon({
                paths: lotBCoords,
                strokeColor: '#FF0000',
                strokeOpacity: 0.8,
                strokeWeight: 2,
                fillColor: '#FF0000',
                fillOpacity: 0.35
            });

            lotB.setMap(map)
            */
            

            
        
            //Great, simple guide for onclick stuff is athttp://vikku.info/programming/google-maps-v3/get-lattitude-longitude-onclick-and-onmouseover-google-map-v3.htm
            //also using http://stackoverflow.com/questions/19087352/capture-coordinates-in-google-map-on-user-click
            //TO FIX PROBLEMS UPDATING ANY BOXES: I need to set the ClientIdMode to Static (I've done this with TextBox7)
            var temp = [];
            var tempTracker = 0;
            google.maps.event.addListener(map, 'click', function (event) {
                //Go through the textboxes and find the earliest one that is empty.
                for (var i = 7; i < 22; i++)
                {
                    
                    if (document.getElementById('TextBox' + i.toString()).value == '')  //I'm So Meta, Even This Acronym
                    {
                        var subTemp = [event.latLng.lat(), event.latLng.lng()];
                        temp[tempTracker] = subTemp; //to create the array of coordinate arrays. Tested and it works
                        tempTracker++;
                        document.getElementById('TextBox' + i.toString()).value = event.latLng.lat();
                        document.getElementById('TextBox' + (i + 1).toString()).value = event.latLng.lng();
                        break;
                        
                    }
                }
                //TO ADD: have a variable outside this function holding the arrays of coordinates, so we can build that into the new parking lot.
            })
        }




        google.maps.event.addDomListener(window, 'load', initialize);

    </script>
    

         <div class="content-wrapper">
           <h1>Parking Management</h1>
             <asp:Panel ID="Panel2" runat="server" Height="909px">
                 <asp:Button ID="Button1" runat="server" Text="Edit Lot" BackColor="Black" BorderColor="Black" ForeColor="White" Width="48%" OnClick="Button1_Click" />
                 <asp:Button ID="Button6" runat="server" BackColor="Black" BorderColor="Black" ForeColor="White" OnClick="Button6_Click" Text="Add Lot" Width="49%" />
                 <asp:MultiView ID="MultiViewManage" runat="server">
                     <asp:View ID="View1" runat="server">
                         <asp:DropDownList ID="DropDownList1" runat="server" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged" Width="120px" AppendDataBoundItems="True" AutoPostBack="True" ClientIDMode="Static">
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList2" runat="server" Width="100px" ClientIDMode="Static">
                             <asp:ListItem Value="1">Faculty</asp:ListItem>
                             <asp:ListItem Value="2">Student</asp:ListItem>
                             <asp:ListItem Value="3">Guest</asp:ListItem>
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList3" runat="server" Width="100px" ClientIDMode="Static">
                             <asp:ListItem Value="1">Open</asp:ListItem>
                             <asp:ListItem Value="0">Closed</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label5" runat="server" Font-Size="Small" Text="Columns"></asp:Label>
                         <asp:TextBox ID="TextBox1" runat="server" Height="16px" Width="50px" ClientIDMode="Static"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label6" runat="server" Font-Size="Small" Text="Rows"></asp:Label>
                         <asp:TextBox ID="TextBox2" runat="server" Width="50px" ClientIDMode="Static"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button7" runat="server" BackColor="Black" ForeColor="White" OnClick="Button7_Click" Text="Save" Width="100px" />
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button8" runat="server" BackColor="Black" ForeColor="White" OnClick="Button8_Click" Text="Undo" Width="100px" />
                         <br />
                         <br />
                         <asp:Label ID="Label7" runat="server" Font-Size="Medium" Text="Edit Parking Space:"></asp:Label>
                         <br />
                         <asp:DropDownList ID="DropDownList4" runat="server" Width="100px" ClientIDMode="Static" OnSelectedIndexChanged="DropDownList4_SelectedIndexChanged">
                             <asp:ListItem Value="0">Column</asp:ListItem>
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList5" runat="server" Width="100px" ClientIDMode="Static" OnSelectedIndexChanged="DropDownList5_SelectedIndexChanged">
                             <asp:ListItem Value="0">Row</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList6" runat="server" Width="100px" ClientIDMode="Static">
                             <asp:ListItem Value="0">Status</asp:ListItem>
                             <asp:ListItem>Available</asp:ListItem>
                             <asp:ListItem>Reserved</asp:ListItem>
                             <asp:ListItem>In Use</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label8" runat="server" Font-Size="Small" Text="User:"></asp:Label>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:TextBox ID="TextBox3" runat="server" Width="188px" ClientIDMode="Static"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button9" runat="server" BackColor="Black" ForeColor="White" OnClick="Button9_Click" Text="Save" Width="100px" />
                         &nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button10" runat="server" BackColor="Black" ForeColor="White" OnClick="Button10_Click" Text="Undo" Width="100px" />
                     </asp:View>
                     <asp:View ID="View2" runat="server">
                         <asp:Panel ID="Panel1" runat="server" Height="460px" Width="933">
                             <div id="map-canvas">
                             <asp:Label ID="Label4" runat="server" Height="450px" Width="5px"></asp:Label>
                             <br />
                             <br />
                             <br />
                             </div>
                         </asp:Panel>
                        <div style="margin-top:100px">
                         <asp:Panel ID="Panel3" runat="server" Width="950px">
                             <asp:Panel ID="Panel4" runat="server" BorderColor="#666666" BorderStyle="Solid">
                                 &nbsp;&nbsp;
                                 <asp:Label ID="Label11" runat="server" Font-Size="Medium" Text="Name: "></asp:Label>
                                 &nbsp;&nbsp;
                                 <asp:TextBox ID="TextBox4" runat="server" Width="80px"></asp:TextBox>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="Label9" runat="server" Font-Size="Medium" Text="Rows:"></asp:Label>
                                 &nbsp;
                                 <asp:TextBox ID="TextBox5" runat="server" Width="80px"></asp:TextBox>
                                 &nbsp;&nbsp;
                                 <asp:Label ID="Label10" runat="server" Font-Size="Medium" Text="Columns:"></asp:Label>
                                 &nbsp;
                                 <asp:TextBox ID="TextBox6" runat="server" Width="80px"></asp:TextBox>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:DropDownList ID="DropDownList7" runat="server" Height="26px" Width="140px" Font-Size="Medium">
                                     <asp:ListItem Value="0">Designation</asp:ListItem>
                                     <asp:ListItem Value="1">Faculty</asp:ListItem>
                                     <asp:ListItem Value="2">Student</asp:ListItem>
                                     <asp:ListItem Value="3">Guest</asp:ListItem>
                                 </asp:DropDownList>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                 <asp:DropDownList ID="DropDownList8" runat="server" Width="140px" Font-Size="Medium" Height="26px">
                                     <asp:ListItem Value="1">Status</asp:ListItem>
                                     <asp:ListItem Value="1">Open</asp:ListItem>
                                     <asp:ListItem Value="0">Closed</asp:ListItem>
                                 </asp:DropDownList> 
                             </asp:Panel>
                             <br />
                             <asp:Panel ID="Panel5" runat="server" Height="345px">
                                 &nbsp;<asp:Label ID="Label12" runat="server" Font-Size="Medium" Text="Latitude:" Width="198px"></asp:Label>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:Label ID="Label13" runat="server" Font-Size="Medium" Text="Longitude:" Width="205px"></asp:Label>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:Label ID="Label14" runat="server" Font-Size="Medium" Text="Latitude:" Width="195px"></asp:Label>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:Label ID="Label15" runat="server" Font-Size="Medium" Text="Longitude:" Width="180px"></asp:Label>
                                 <br />
                                 <asp:Panel ID="Panel6" runat="server" BorderColor="#CCCCCC" BorderStyle="Solid" BorderWidth="2px" CssClass="float-left" Height="165px" Width="471px">
                                     &nbsp;<asp:TextBox ID="TextBox7" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox8" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />
                                     &nbsp;<asp:TextBox ID="TextBox9" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox10" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />
                                     &nbsp;<asp:TextBox ID="TextBox11" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox12" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />
                                     &nbsp;<asp:TextBox ID="TextBox13" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox14" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                 </asp:Panel>
                                 <asp:Panel ID="Panel8" runat="server" BorderColor="#CCCCCC" BorderStyle="Solid" BorderWidth="2px" CssClass="float-left" Height="165px" Width="471px">
                                     &nbsp;<asp:TextBox ID="TextBox15" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox16" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />
                                     &nbsp;<asp:TextBox ID="TextBox17" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox18" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />
                                     &nbsp;<asp:TextBox ID="TextBox19" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox20" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                     <br />                                  
                                     &nbsp;<asp:TextBox ID="TextBox21" runat="server" ClientIDMode="Static" Width="216px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox22" runat="server" Width="216px" ClientIDMode="Static"></asp:TextBox>
                                 </asp:Panel>
                                 <br />
                                 <asp:Button ID="Button11" runat="server" BackColor="Black" CssClass="float-left" Font-Size="Medium" ForeColor="White" Text="Submit" Width="465px" ClientIDMode="Static" OnClick="Button11_Click" />
                                 <asp:Button ID="Button12" runat="server" BackColor="Black" CssClass="float-right" Font-Size="Medium" ForeColor="White" Text="Clear" Width="465px" ClientIDMode="Static" />
                                 <br />
                             </asp:Panel>
                             <br />
                             <br />
                             <br />
                         </asp:Panel>
                        </div>
                     </asp:View>
                 </asp:MultiView>
             </asp:Panel>
        </div>
    </section>
</asp:Content>